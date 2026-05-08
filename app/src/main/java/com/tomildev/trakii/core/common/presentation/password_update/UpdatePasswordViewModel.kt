package com.tomildev.trakii.core.common.presentation.password_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.UpdatePasswordUseCase
import com.tomildev.trakii.core.domain.use_case.session.LogoutUseCase
import com.tomildev.trakii.core.domain.use_case.session.SetPasswordRecoveryInProgressUseCase
import com.tomildev.trakii.core.domain.use_case.session.SetReauthenticationRequiredUseCase
import com.tomildev.trakii.core.domain.use_case.user.UserValidationUseCases
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.core.navigation.NavRoute
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
    private val setReauthenticationRequired: SetReauthenticationRequiredUseCase,
    private val setPasswordRecoveryInProgress: SetPasswordRecoveryInProgressUseCase,
    private val logout: LogoutUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val origin = savedStateHandle.toRoute<NavRoute.Auth.ForgotPasswordReset>().origin

    private val _uiState = MutableStateFlow(UpdatePasswordUiState(origin = origin))
    val uiState: StateFlow<UpdatePasswordUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UpdatePasswordUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

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
                    handlePasswordUpdated()
                }
            }
        }
    }

    fun onBackClick() {
        _uiState.update { it.copy(showCancelUpdateDialog = true) }
    }

    fun onDismissCancelUpdateDialog() {
        _uiState.update { it.copy(showCancelUpdateDialog = false) }
    }

    fun onConfirmCancelUpdateDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(showCancelUpdateDialog = false) }
            when (origin) {
                NavRoute.PasswordUpdateOrigin.PasswordRecovery -> {
                    logout()
                    setPasswordRecoveryInProgress(false)
                    _uiEvents.send(
                        UpdatePasswordUiEvent.NavigateToSignIn(
                            showPasswordUpdatedSnackbar = false
                        )
                    )
                }

                NavRoute.PasswordUpdateOrigin.AccountSettings -> {
                    _uiEvents.send(UpdatePasswordUiEvent.NavigateToAccountSettings)
                }
            }
        }
    }

    private suspend fun handlePasswordUpdated() {
        when (origin) {
            NavRoute.PasswordUpdateOrigin.PasswordRecovery -> {
                logout()
                setPasswordRecoveryInProgress(false)
                _uiEvents.send(
                    UpdatePasswordUiEvent.NavigateToSignIn(
                        showPasswordUpdatedSnackbar = true
                    )
                )
            }

            NavRoute.PasswordUpdateOrigin.AccountSettings -> {
                setReauthenticationRequired(true)
                logout()
                _uiEvents.send(
                    UpdatePasswordUiEvent.NavigateToSignIn(
                        showPasswordUpdatedSnackbar = false
                    )
                )
            }
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
