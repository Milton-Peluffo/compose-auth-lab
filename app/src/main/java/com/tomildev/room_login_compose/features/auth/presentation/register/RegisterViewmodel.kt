package com.tomildev.room_login_compose.features.auth.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.features.auth.domain.model.User
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onRegisterClick() {
        if (validateFields()) {
            registerUser()
        }
    }

    private fun validateFields(): Boolean {
        return when {

            !isNameLengthValid(name = _uiState.value.name) -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Name must have 3 characters at least",
                        isNameError = true
                    )
                }
                false
            }

            !isPhoneLengthValid(phone = _uiState.value.phone) -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Phone must have 10 characters",
                        isPhoneError = true
                    )
                }
                false
            }

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

            !isPasswordMatched(
                password = _uiState.value.password,
                confirmPassword = _uiState.value.confirmPassword
            ) -> {
                _uiState.update {
                    it.copy(
                        errorMessage = "Password should match",
                        isPasswordConfirmError = true
                    )
                }
                false
            }

            else -> true
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            authRepository.registerUser(
                user = User(
                    _uiState.value.name,
                    _uiState.value.phone,
                    _uiState.value.email,
                    _uiState.value.password
                )
            )
            _uiState.update { currentState ->
                currentState.copy(isRegistrationSuccess = true)
            }
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

    fun onPhoneChange(phone: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phone = phone,
                errorMessage = null,
                isPhoneError = false
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

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordMatched(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    private fun isPasswordLengthValid(password: String): Boolean = password.length >= 8
    private fun isPhoneLengthValid(phone: String): Boolean = phone.length == 10
    private fun isNameLengthValid(name: String): Boolean = name.length >= 3
}

data class RegisterUiState(
    //USER DATA
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    //VALIDATORS
    val isRegistered: Boolean = false,
    val isRegistrationSuccess: Boolean = false,
    //ERRORS
    val errorMessage: String? = null,
    val isNameError: Boolean = false,
    val isPhoneError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isPasswordConfirmError: Boolean = false,
)