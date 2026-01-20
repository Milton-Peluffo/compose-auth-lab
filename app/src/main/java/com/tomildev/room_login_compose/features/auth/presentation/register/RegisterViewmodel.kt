package com.tomildev.room_login_compose.features.auth.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.features.auth.domain.model.User
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewmodel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onValidateUserFields() {

        if (!isEmailValid(email = _uiState.value.email)
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Invalid email format",
                    isEmailError = true
                )
            }

        } else if (!isPasswordValid(_uiState.value.password)) {
            _uiState.update {
                it.copy(
                    errorMessage = "Password must have 8 character at least",
                    isPasswordError = true
                )
            }
        } else if (!isPasswordMatched(
                _uiState.value.password,
                _uiState.value.confirmPassword
            )
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Password should match",
                    isPasswordConfirmError = true
                )
            }
        } else {
            onRegisterUser()
        }
    }

    fun onRegisterUser() {
        viewModelScope.launch {
            authRepository.registerUser(user = User(_uiState.value.email, _uiState.value.password))
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

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordMatched(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    private fun isPasswordValid(password: String): Boolean = password.length >= 8
}

data class RegisterUiState(
    //USER DATA
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    //VALIDATORS
    val isRegistered: Boolean = false,
    val isRegistrationSuccess: Boolean = false,
    //ERRORS
    val errorMessage: String? = null,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isPasswordConfirmError: Boolean = false,
)