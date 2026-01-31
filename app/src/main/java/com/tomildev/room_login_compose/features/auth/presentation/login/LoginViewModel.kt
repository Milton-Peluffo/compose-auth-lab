package com.tomildev.room_login_compose.features.auth.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun onLoginClick() {
        if (validateFields()) {
            userLogin()
        }
    }

    private fun validateFields(): Boolean {
        return when {

            !isEmailValid(email = _uiState.value.email) -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Invalid email format",
                        isEmailError = true
                    )
                }
                false
            }

            !isPasswordLengthValid(password = _uiState.value.password) -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Password must have 8 character at least",
                        isPasswordError = true
                    )
                }
                false
            }

            else -> true
        }
    }

    private fun userLogin() {

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = userRepository.getUserByEmail(_uiState.value.email)

            result.onSuccess { user ->
                if (user != null && user.password == _uiState.value.password) {
                    delay(2500)
                    userRepository.saveUserSession(user.id)
                    _uiState.update { it.copy(isLoginSuccess = true) }

                } else {
                    _uiState.update {
                        it.copy(errorMessage = "Email or password incorrect")
                    }
                }
            }.onFailure {
                _uiState.update { it.copy(errorMessage = "An error occurred") }
            }
            _uiState.update { it.copy(isLoading = false) }
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

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordLengthValid(password: String): Boolean = password.length >= 8

}

data class LoginUiState(
    //USER DATA
    val email: String = "",
    val password: String = "",
    //VALIDATORS
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    //ERRORS
    val errorMessage: String? = null,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
)