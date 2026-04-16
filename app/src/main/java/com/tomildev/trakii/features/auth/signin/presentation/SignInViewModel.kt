package com.tomildev.trakii.features.auth.signin.presentation

import androidx.lifecycle.ViewModel
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()


    fun onLoginClick() {
        if (validateFields()) {
            userLogin()
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

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

        updateErrorState()
        return true
    }

    private fun updateErrorState(
        emailError: UserValidationError? = null,
        passwordError: UserValidationError? = null,
    ) {
        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError,
            )
        }
    }

    private fun userLogin() {
//
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//            val result = userRepository.getUserByEmail(_uiState.value.email)
//
//            result.onSuccess { user ->
//                if (user != null && user.password == _uiState.value.password) {
//                    delay(2500)
//                    userRepository.saveUserSession(user.id)
//                    _uiState.update { it.copy(isLoginSuccess = true) }
//
//                } else {
//                    _uiState.update {
//                        it.copy(errorMessage = "Email or password incorrect")
//                    }
//                }
//            }.onFailure {
//                _uiState.update { it.copy(errorMessage = "An error occurred") }
//            }
//            _uiState.update { it.copy(isLoading = false) }
//        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = null,
                errorMessage = null,
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                passwordError = null,
                errorMessage = null,
            )
        }
    }
}