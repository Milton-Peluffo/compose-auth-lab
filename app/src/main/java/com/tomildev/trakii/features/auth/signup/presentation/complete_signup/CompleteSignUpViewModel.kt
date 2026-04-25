package com.tomildev.trakii.features.auth.signup.presentation.complete_signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.UserUseCases
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteSignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompleteSignUpUiState())
    val uiState: StateFlow<CompleteSignUpUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<CompleteSignUpUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        val email = savedStateHandle.get<String>("email") ?: ""
        _uiState.update { it.copy(email = email) }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun onCompleteSignUp() {
        if (validateFields()) {
            completeSignUp()
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        val nameResult = userUseCases.validateName.execute(state.name)
        if (nameResult is UserValidationResult.Error) {
            _uiState.update { it.copy(nameError = nameResult.error) }
            return false
        }

        val passwordResult = userUseCases.validatePassword.execute(state.password)
        if (passwordResult is UserValidationResult.Error) {
            _uiState.update { it.copy(passwordError = passwordResult.error) }
            return false
        }

        val confirmPasswordResult = userUseCases.validateConfirmPassword.execute(
            state.password,
            state.confirmPassword
        )
        if (confirmPasswordResult is UserValidationResult.Error) {
            _uiState.update { it.copy(confirmPasswordError = confirmPasswordResult.error) }
            return false
        }

        return true
    }

    private fun completeSignUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.update { it.copy(isLoading = false) }
            _uiEvents.send(CompleteSignUpUiEvent.Success)
        }
    }
}