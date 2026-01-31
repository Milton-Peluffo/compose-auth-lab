package com.tomildev.room_login_compose.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.core.domain.repository.UserRepository
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
class SettingsViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<SettingsUiEvent>()
    val events = _eventChannel.receiveAsFlow()


    init {
        fetchCurrentUser()
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            userRepository.getCurrentUser().collect { user ->
                user?.let { data ->
                    _uiState.update { settingsUiState ->
                        settingsUiState.copy(
                            name = data.name,
                            email = data.email
                        )
                    }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.closeUserSession()
            delay(1500)
            _uiState.update { it.copy(isLoading = false) }
            _eventChannel.send(SettingsUiEvent.NavigateToLogin)
        }
    }
}

sealed class SettingsUiEvent {
    data object NavigateToLogin : SettingsUiEvent()
}

data class SettingsUiState(
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
)