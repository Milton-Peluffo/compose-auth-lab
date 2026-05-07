package com.tomildev.trakii.features.auth.signin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.UserValidationUseCases
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.AccountSettingsUseCases
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
class SignInViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userValidationUseCases: UserValidationUseCases,
    private val accountSettingsUseCases: AccountSettingsUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<SignInUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        observeReauthenticationRequired()
    }

    private fun observeReauthenticationRequired() {
        viewModelScope.launch {
            accountSettingsUseCases.observeReauthenticationRequired().collect { required ->
                _uiState.update { it.copy(showReauthenticationRequiredDialog = required) }
            }
        }
    }

    fun onSignInClick() {
        if (validateFields()) {
            signIn()
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        val emailResult = userValidationUseCases.validateEmail(email = state.email)
        if (emailResult is UserValidationResult.Error) {
            updateErrorState(emailError = emailResult.error)
            return false
        }

        val passwordResult = userValidationUseCases.validatePassword(password = state.password)
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
        networkError: DataError? = null
    ) {
        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError,
                networkError = networkError
            )
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, networkError = null) }
            val result = authUseCases.signInWithEmail(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Success -> {
                    _uiEvents.send(SignInUiEvent.NavigateToHabitList(_uiState.value.email))
                }

                is Result.Error -> {
                    _uiState.update { it.copy(networkError = result.error) }
                    if (result.error == DataError.Network.NoInternet || result.error == DataError.Network.Timeout) {
                        _uiEvents.send(SignInUiEvent.Warning(result.error))
                    } else {
                        _uiEvents.send(SignInUiEvent.Error(result.error))
                    }
                }
            }
        }
    }

    fun onGoogleSignInStart() {
        _uiState.update { it.copy(isGoogleLoading = true) }
    }

    fun onGoogleSignInCancel() {
        _uiState.update { it.copy(isGoogleLoading = false) }
    }

    fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = authUseCases.authWithGoogle(idToken)
            _uiState.update { it.copy(isGoogleLoading = false) }

            when (result) {
                is Result.Success -> {
                    _uiEvents.send(SignInUiEvent.NavigateToHabitList(""))
                }

                is Result.Error -> {
                    if (result.error == DataError.Network.NoInternet || result.error == DataError.Network.Timeout) {
                        _uiEvents.send(SignInUiEvent.Warning(result.error))
                    } else {
                        _uiEvents.send(SignInUiEvent.Error(result.error))
                    }
                }
            }
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = null,
                networkError = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                passwordError = null,
                networkError = null
            )
        }
    }

    fun onConfirmReauthenticationRequiredDialog() {
        viewModelScope.launch {
            accountSettingsUseCases.logout()
            accountSettingsUseCases.setReauthenticationRequired(false)
            _uiState.update { it.copy(showReauthenticationRequiredDialog = false) }
        }
    }

    fun onDismissReauthenticationRequiredDialog() {
        // Mandatory dialog. The user must acknowledge before continuing.
    }
}
