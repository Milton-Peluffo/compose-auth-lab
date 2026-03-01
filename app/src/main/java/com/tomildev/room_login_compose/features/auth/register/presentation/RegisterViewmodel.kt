package com.tomildev.room_login_compose.features.auth.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.core.domain.model.user.User
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationResult
import com.tomildev.room_login_compose.core.domain.use_case.user.UserUseCases
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the user registration process.
 *
 * It centralizes the UI state through [RegisterUiState] and orchestrates
 * input validation via [UserUseCases] before proceeding with account
 * creation in [AuthRepository].
 */

@HiltViewModel
class RegisterViewmodel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onRegisterClick() {
        if (validateFields()) {
//            registerUser()
        }
    }

    private fun validateFields(): Boolean {


        val nameResult =
            userUseCases.validateName.execute(name = _uiState.value.name)
        val emailResult =
            userUseCases.validateEmail.execute(email = _uiState.value.email)
        val passwordResult =
            userUseCases.validatePassword.execute(password = _uiState.value.password)
        val passwordConfirmResult =
            userUseCases.validateConfirmPassword.execute(
                password = _uiState.value.password,
                confirmPassword = _uiState.value.confirmPassword
            )

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            passwordConfirmResult
        ).any { it is UserValidationResult.Error }

        _uiState.update {
            it.copy(
                nameError = if (nameResult is UserValidationResult.Error) nameResult.error else null,
                emailError = if (emailResult is UserValidationResult.Error) emailResult.error else null,
                passwordError = if (passwordResult is UserValidationResult.Error) passwordResult.error else null,
                passwordConfirmError = if (passwordConfirmResult is UserValidationResult.Error) passwordConfirmResult.error else null,

                isNameError = nameResult is UserValidationResult.Error,
                isEmailError = emailResult is UserValidationResult.Error,
                isPasswordError = passwordResult is UserValidationResult.Error,
                isPasswordConfirmError = passwordConfirmResult is UserValidationResult.Error,
            )
        }
        return !hasError
    }

    fun registerUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            authRepository.registerUser(
                user = User(
                    id = "",
                    name = _uiState.value.name,
                    email = _uiState.value.email
                ),
                password = _uiState.value.password
            )
            _uiState.update { currentState ->
                delay(2500)
                currentState.copy(isRegistrationSuccess = true)
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                errorMessage = null,
                isNameError = false
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                errorMessage = null,
                isEmailError = false
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                errorMessage = null,
                isPasswordError = false
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword,
                errorMessage = null,
                isPasswordConfirmError = false
            )
        }
    }
}

data class RegisterUiState(
    //USER DATA
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    //VALIDATORS
    val isRegistered: Boolean = false,
    val isRegistrationSuccess: Boolean = false,
    val isLoading: Boolean = false,
    //ERRORS
    val errorMessage: String? = null,
    val isNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val nameError: UserValidationError? = null,
    val emailError: UserValidationError? = null,
    val passwordError: UserValidationError? = null,
    val passwordConfirmError: UserValidationError? = null,
    val isPasswordConfirmError: Boolean = false,
)