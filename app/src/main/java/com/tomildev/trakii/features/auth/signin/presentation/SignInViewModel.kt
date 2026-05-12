package com.tomildev.trakii.features.auth.signin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signin.domain.use_case.AuthWithGoogleUseCase
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
    private val authWithGoogleUseCase: AuthWithGoogleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<SignInUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onGoogleSignInStart() {
        _uiState.update { it.copy(isGoogleLoading = true) }
    }

    fun onGoogleSignInCancelled() {
        _uiState.update {
            it.copy(isGoogleLoading = false)
        }
    }
    fun onGoogleSignInError(error: DataError.Network) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isGoogleLoading = false)
            }
            if (
                error == DataError.Network.NoInternet ||
                error == DataError.Network.Timeout
            ) {
                _uiEvents.send(
                    SignInUiEvent.Warning(error)
                )
            } else {
                _uiEvents.send(
                    SignInUiEvent.Error(error)
                )
            }
        }
    }

    fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = authWithGoogleUseCase(idToken)
            _uiState.update { it.copy(isGoogleLoading = false) }

            when (result) {
                is Result.Success -> {
                    _uiEvents.send(SignInUiEvent.NavigateToHabitList)
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
}
