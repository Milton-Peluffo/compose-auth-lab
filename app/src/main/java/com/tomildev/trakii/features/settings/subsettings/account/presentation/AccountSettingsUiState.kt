package com.tomildev.trakii.features.settings.subsettings.account.presentation

data class AccountSettingsUiState(
    val name: String = "",
    val email: String = "",
    val accountType: String = "",
    val accountCreationDate: String = "",
    val loadingState: LoadingState = LoadingState.None,
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
)

sealed class LoadingState {
    object None : LoadingState()
    object LoggingOut : LoadingState()
    object DeletingAccount : LoadingState()
}