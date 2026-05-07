package com.tomildev.trakii.features.settings.main_settings.presentation

data class SettingsUiState(
    val name: String = "",
    val email: String = "",
    val loadingState: LoadingState = LoadingState.None,
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
)

sealed class LoadingState {
    object None : LoadingState()
    object LoggingOut : LoadingState()
    object DeletingAccount : LoadingState()
}