package com.tomildev.trakii.features.auth.signup.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.model.error.DataError
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
class SignUpViewmodel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userValidationUseCases: UserValidationUseCases
) : ViewModel() {

    private val _uiEvents = Channel<SignUpUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onSignUpClick() {
        if (validateFields()) {
            sendOtp()
        }
    }

    private fun validateFields(): Boolean {
        val emailResult = userValidationUseCases.validateEmail(email = _uiState.value.email)
        if (emailResult is UserValidationResult.Error) {
            _uiState.update { it.copy(emailError = emailResult.error) }
            return false
        }
        _uiState.update { it.copy(emailError = null) }
        return true
    }

    private fun sendOtp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val email = _uiState.value.email

            val result = authUseCases.sendOtp(email)

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    val error = result.error
                    if (error == DataError.Network.NoInternet || error == DataError.Network.Timeout) {
                        _uiEvents.send(SignUpUiEvent.Warning(error))
                    } else {
                        _uiEvents.send(SignUpUiEvent.Error(error))
                    }
                }
                is Result.Success -> {
                    _uiEvents.send(SignUpUiEvent.NavigateToOtp(email.trim().lowercase()))
                }
            }
        }
    }

    fun onGoogleSignInStart() {
        _uiState.update { it.copy(isGoogleLoading = true) }
    }

    fun onGoogleSignInCancel() {
        _uiState.update { it.copy(isGoogleLoading = false) }
    }

    fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = authUseCases.authWithGoogle(idToken)
            _uiState.update { it.copy(isGoogleLoading = false) }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.NoInternet || result.error == DataError.Network.Timeout) {
                        _uiEvents.send(SignUpUiEvent.Warning(result.error))
                    } else {
                        _uiEvents.send(SignUpUiEvent.Error(result.error))
                    }
                }
                is Result.Success -> {
                    _uiEvents.send(SignUpUiEvent.NavigateToHome)
                }
            }
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = null,
                errorMessage = null,
            )
        }
    }
}
