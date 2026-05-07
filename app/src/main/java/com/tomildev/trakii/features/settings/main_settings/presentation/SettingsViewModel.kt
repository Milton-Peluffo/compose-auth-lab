package com.tomildev.trakii.features.settings.main_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
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
class SettingsViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<SettingsUiEvent>()
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
                            email = sessionState.user.email
                        )
                    }
                }
            }
        }
    }
}