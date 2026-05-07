package com.tomildev.trakii.features.settings.subsettings.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import com.tomildev.trakii.core.domain.repository.UserRepository
import com.tomildev.trakii.core.domain.util.formatDate
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val authUseCases: AuthUseCases
) : ViewModel() {


    private val _uiState = MutableStateFlow(AccountSettingsUiState())
    val uiState: StateFlow<AccountSettingsUiState> = _uiState.asStateFlow()

    private val _eventChannel =
        Channel<AccountSettingsUiEvent>()
    val events = _eventChannel.receiveAsFlow()


    init {
        fetchCurrentUser()
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            sessionRepository.observeSession().collect { sessionState ->
                if (sessionState is SessionState.Authenticated) {
                    _uiState.update { settingsUiState ->
                        settingsUiState.copy(
                            name = sessionState.user.name,
                            email = sessionState.user.email,
                            accountCreationDate = formatDate(sessionState.user.createdAt.toString()),
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingState = LoadingState.LoggingOut) }

            try {
                authUseCases.logout()
                delay(500)
                _eventChannel.send(AccountSettingsUiEvent.NavigateToSignIn)

            } catch (e: Exception) {
            } finally {
                _uiState.update { it.copy(loadingState = LoadingState.None) }
            }
        }
    }

    fun onLogoutClick() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun onConfirmLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
        logout()
    }

    fun onDismissLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }
}