package com.tomildev.room_login_compose.features.auth.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewmodel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    fun onRegisterUser() {

        if (isEmailValid(email = _uiState.value.email) && isPasswordMatched(
                _uiState.value.password,
                _uiState.value.confirmPassword
            )
        ) {
            _uiState.update {
                it.copy(
                    isRegistrationSuccess = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isRegistrationSuccess = false
                )
            }
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword
            )
        }
    }

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordMatched(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

//    private fun isPasswordValid(password: String): Boolean = password.length >= 6
}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isRegistered: Boolean = false,
    val isRegistrationSuccess: Boolean = false,
)