package com.tomildev.trakii.features.settings.main_settings.presentation

import androidx.lifecycle.ViewModel
import com.tomildev.trakii.core.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        sessionRepository.getCachedUser().let { user ->
            SettingsUiState(
                name = user?.displayName.orEmpty(),
                email = user?.email.orEmpty()
            )
        }
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<SettingsUiEvent>()
    val events = _eventChannel.receiveAsFlow()
}