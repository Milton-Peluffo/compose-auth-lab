package com.tomildev.room_login_compose.features.auth.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.core.domain.model.user.User
import com.tomildev.room_login_compose.core.domain.model.user.ValidationError
import com.tomildev.room_login_compose.core.domain.model.user.ValidationResult
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

@HiltViewModel
class RegisterViewmodel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onRegisterClick() {
        val passwordResult =
            userUseCases.validatePassword.execute(password = _uiState.value.password)

        when (passwordResult) {
            is ValidationResult.Success -> {
                registerUser()
            }

            is ValidationResult.Error -> {
                _uiState.update {
                    it.copy(
                        passwordError = passwordResult.error,
                        isPasswordError = true
                    )
                }
            }
        }

    }

//    private fun validateFields(): Boolean {
//        return when {
//
//            !isNameLengthValid(name = _uiState.value.name) -> {
//                _uiState.update {
//                    it.copy(
//                        errorMessage = "Name must have 3 characters at least",
//                        isNameError = true
//                    )
//                }
//                false
//            }
//
//            !isEmailValid(email = _uiState.value.email) -> {
//                _uiState.update {
//                    it.copy(
//                        errorMessage = "Invalid email format",
//                        isEmailError = true
//                    )
//                }
//                false
//            }
//
//            !isPasswordLengthValid(password = _uiState.value.password) -> {
//                _uiState.update {
//                    it.copy(
//                        errorMessage = "Password must have 8 character at least",
//                        isPasswordError = true
//                    )
//                }
//                false
//            }
//
//            !isPasswordMatched(
//                password = _uiState.value.password,
//                confirmPassword = _uiState.value.confirmPassword
//            ) -> {
//                _uiState.update {
//                    it.copy(
//                        errorMessage = "Password should match",
//                        isPasswordConfirmError = true
//                    )
//                }
//                false
//            }
//
//            !_uiState.value.isCheckBoxChecked -> {
//                _uiState.update {
//                    it.copy(
//                        errorMessage = "You must accept the Terms and Conditions to continue",
//                        isTermsAndConditionsError = true
//                    )
//                }
//                false
//            }
//
//            else -> true
//        }
//    }

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

    fun onCheckedChange(isCheckBoxChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isCheckBoxChecked = isCheckBoxChecked,
                errorMessage = null,
                isTermsAndConditionsError = false
            )
        }
    }

//    private fun isPasswordMatched(password: String, confirmPassword: String): Boolean =
//        password == confirmPassword

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
    val isCheckBoxChecked: Boolean = false,
    //ERRORS
    val errorMessage: String? = null,
    val isNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val passwordError: ValidationError? = null,
    val isPasswordConfirmError: Boolean = false,
    val isTermsAndConditionsError: Boolean = false,
)