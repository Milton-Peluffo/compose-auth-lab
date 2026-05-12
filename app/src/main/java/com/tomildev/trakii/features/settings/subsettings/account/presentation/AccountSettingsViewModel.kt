package com.tomildev.trakii.features.settings.subsettings.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.core.domain.util.formatDate
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.AccountUseCasesWrapper
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
    private val sessionRepository: SessionRepository,
    private val accountUseCasesWrapper: AccountUseCasesWrapper,
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
                            name = sessionState.user.displayName,
                            editedName = sessionState.user.displayName,
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
                accountUseCasesWrapper.logout()
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

    fun onEditNameClick() {
        _uiState.update {
            it.copy(
                showEditNameDialog = true,
                editedName = it.name,
                nameError = null
            )
        }
    }

    fun onDismissEditNameDialog() {
        _uiState.update { it.copy(showEditNameDialog = false, nameError = null) }
    }

    fun onEditedNameChange(name: String) {
        _uiState.update { it.copy(editedName = name, nameError = null) }
    }

    fun onConfirmEditNameDialog() {
        val nameResult = accountUseCasesWrapper.validateNameUseCase(_uiState.value.editedName)
        if (nameResult is UserValidationResult.Error) {
            _uiState.update { it.copy(nameError = nameResult.error) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(loadingState = LoadingState.UpdatingName) }
            val result = accountUseCasesWrapper.updateDisplayName(_uiState.value.editedName)
            _uiState.update { it.copy(loadingState = LoadingState.None) }

            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            name = it.editedName.trim(),
                            showEditNameDialog = false,
                            nameError = null
                        )
                    }
                }

                is Result.Error -> sendErrorEvent(result.error)
            }
        }
    }

    private suspend fun sendErrorEvent(error: DataError.Network) {
        when (error) {
            DataError.Network.NoInternet,
            DataError.Network.Timeout -> _eventChannel.send(AccountSettingsUiEvent.Warning(error))

            else -> _eventChannel.send(AccountSettingsUiEvent.Error(error))
        }
    }
}
