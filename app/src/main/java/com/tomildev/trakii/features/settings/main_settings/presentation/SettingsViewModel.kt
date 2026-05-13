package com.tomildev.trakii.features.settings.main_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.features.settings.main_settings.domain.use_case.LogoutUseCase
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
class SettingsViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        sessionRepository.getCachedUser().let { user ->
            SettingsUiState(
                name = user?.displayName.orEmpty(),
                avatarUrl = user?.avatarUrl.orEmpty(),
                email = user?.email.orEmpty()
            )
        }
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<SettingsUiEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingState = LoadingState.LoggingOut) }
            delay(1500)
            try {
                logoutUseCase()
                _eventChannel.send(SettingsUiEvent.NavigateToSignIn)

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