package com.tomildev.trakii.features.auth.forgot_password.email_request.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.UserValidationUseCases
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailRequestViewmodel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userValidationUseCases: UserValidationUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmailRequestUiState())
    val uiState: StateFlow<EmailRequestUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<EmailRequestUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onSendOtpVerificationRequest() {
        if (validateFields()) {
            sendOtp()
        }
    }

    fun sendOtp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val email = _uiState.value.email

            val result = authUseCases.sendResetOtp(email)

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    val error = result.error
                    if (error == DataError.Network.NoInternet || error == DataError.Network.Timeout) {
                        _uiEvents.send(EmailRequestUiEvent.Warning(error))
                    } else {
                        _uiEvents.send(EmailRequestUiEvent.Error(error))
                    }
                }

                is Result.Success -> {
                    _uiEvents.send(EmailRequestUiEvent.NavigateToOtp(email.trim().lowercase()))
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        val emailResult = userValidationUseCases.validateEmail(email = state.email)
        if (emailResult is UserValidationResult.Error) {
            updateErrorState(emailError = emailResult.error)
            return false
        }

        updateErrorState()
        return true
    }

    private fun updateErrorState(
        emailError: UserValidationError? = null,
        networkError: DataError? = null
    ) {
        _uiState.update {
            it.copy(
                emailError = emailError,
                networkError = networkError
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = null,
                networkError = null
            )
        }
    }
}
