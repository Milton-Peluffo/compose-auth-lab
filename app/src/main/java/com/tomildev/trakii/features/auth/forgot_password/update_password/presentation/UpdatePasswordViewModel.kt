package com.tomildev.trakii.features.auth.forgot_password.update_password.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.UserValidationUseCases
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.core.navigation.NavRoute
import com.tomildev.trakii.features.auth.forgot_password.update_password.domain.use_case.UpdatePasswordUseCase
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.AccountSettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val userValidationUseCases: UserValidationUseCases,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val accountSettingsUseCases: AccountSettingsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UpdatePasswordUiState())
    val uiState: StateFlow<UpdatePasswordUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UpdatePasswordUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()
    private val isAccountUpdate = savedStateHandle.toRoute<NavRoute.Auth.ForgotPasswordReset>().isAccountUpdate

    fun onConfirmChanges() {
        if (validateFields()) {
            changePassword()
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        val passwordResult = userValidationUseCases.validatePassword(state.password)
        if (passwordResult is UserValidationResult.Error) {
            _uiState.update { it.copy(passwordError = passwordResult.error) }
            return false
        }

        val confirmPasswordResult = userValidationUseCases.validateConfirmPassword(
            state.password,
            state.confirmPassword
        )
        if (confirmPasswordResult is UserValidationResult.Error) {
            _uiState.update { it.copy(confirmPasswordError = confirmPasswordResult.error) }
            return false
        }

        return true
    }

    private fun changePassword() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = updatePasswordUseCase(
                password = _uiState.value.password
            )

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    _uiEvents.send(UpdatePasswordUiEvent.Error(result.error))
                }

                is Result.Success -> {
                    if (isAccountUpdate) {
                        accountSettingsUseCases.setReauthenticationRequired(true)
                        _uiEvents.send(UpdatePasswordUiEvent.Success)
                    } else {
                        _uiEvents.send(UpdatePasswordUiEvent.Success)
                    }
                }
            }
        }
    }

    fun onBackClick() {
        if (isAccountUpdate) {
            _uiState.update { it.copy(showCancelUpdateDialog = true) }
        } else {
            viewModelScope.launch { _uiEvents.send(UpdatePasswordUiEvent.NavigateBack) }
        }
    }

    fun onDismissCancelUpdateDialog() {
        _uiState.update { it.copy(showCancelUpdateDialog = false) }
    }

    fun onConfirmCancelUpdateDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(showCancelUpdateDialog = false) }
            _uiEvents.send(UpdatePasswordUiEvent.NavigateBack)
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = null,
            )
        }
        if (password == _uiState.value.confirmPassword) {
            _uiState.update { it.copy(isPasswordMatch = true) }
        } else {
            _uiState.update { it.copy(isPasswordMatch = false) }
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = null
            )
        }
        if (confirmPassword == _uiState.value.password) {
            _uiState.update { it.copy(isPasswordMatch = true) }
        } else {
            _uiState.update { it.copy(isPasswordMatch = false) }
        }
    }
}
