package com.tomildev.room_login_compose.features.auth.signup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.model.user.User
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationResult
import com.tomildev.room_login_compose.core.domain.use_case.user.UserUseCases
import com.tomildev.room_login_compose.core.domain.util.Result
import com.tomildev.room_login_compose.features.auth.signup.domain.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the user registration process.
 *
 * It centralizes the UI state through [SignUpUiState] and orchestrates
 * input validation via [UserUseCases] before proceeding with account
 * creation in [AuthRepository].
 */


@HiltViewModel
class RegisterViewmodel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiEvents = Channel<SignUpUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onRegisterClick() {
        if (validateFields()) {
            registerUser()
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        val nameResult = userUseCases.validateName.execute(name = state.name)
        if (nameResult is UserValidationResult.Error) {
            updateErrorState(nameError = nameResult.error)
            return false
        }

        val emailResult = userUseCases.validateEmail.execute(email = state.email)
        if (emailResult is UserValidationResult.Error) {
            updateErrorState(emailError = emailResult.error)
            return false
        }

        val passwordResult = userUseCases.validatePassword.execute(password = state.password)
        if (passwordResult is UserValidationResult.Error) {
            updateErrorState(passwordError = passwordResult.error)
            return false
        }

        val passwordConfirmResult = userUseCases.validateConfirmPassword.execute(
            password = state.password,
            confirmPassword = state.confirmPassword
        )
        if (passwordConfirmResult is UserValidationResult.Error) {
            updateErrorState(passwordConfirmError = passwordConfirmResult.error)
            return false
        }
        updateErrorState()
        return true
    }

    private fun updateErrorState(
        nameError: UserValidationError? = null,
        emailError: UserValidationError? = null,
        passwordError: UserValidationError? = null,
        passwordConfirmError: UserValidationError? = null
    ) {
        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                passwordConfirmError = passwordConfirmError,
            )
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = signUpRepository.signUp(
                user = User(
                    id = "",
                    name = _uiState.value.name,
                    email = _uiState.value.email.trim().lowercase()
                ),
                password = _uiState.value.password
            )
            _uiState.update { it.copy(isLoading = false) }
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.NoInternet || result.error == DataError.Network.Timeout) {
                        _uiEvents.send(SignUpUiEvent.Warning(result.error))
                    } else {
                        _uiEvents.send(SignUpUiEvent.Error(result.error))
                    }
                }

                is Result.Success -> {
                    val cleanedEmail = _uiState.value.email.trim().lowercase()
                    viewModelScope.launch {
                        _uiEvents.send(SignUpUiEvent.NavigateToOtp(cleanedEmail))
                    }
                }
            }
        }
    }

    fun onDismissDialog() {
        _uiState.update {
            it.copy(
                showSuccessDialog = false,
            )
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameError = null,
                errorMessage = null,
            )
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

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = null,
                errorMessage = null,
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword,
                passwordConfirmError = null,
                errorMessage = null,
            )
        }
    }
}