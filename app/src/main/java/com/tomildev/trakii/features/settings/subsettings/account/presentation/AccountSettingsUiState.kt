package com.tomildev.trakii.features.settings.subsettings.account.presentation

import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class AccountSettingsUiState(
    val name: String = "",
    val email: String = "",
    val editedName: String = "",
    val accountType: String = "",
    val accountCreationDate: String = "",
    val loadingState: LoadingState = LoadingState.None,
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
    val showUpdatePasswordDialog: Boolean = false,
    val showEditNameDialog: Boolean = false,
    val nameError: UserValidationError? = null
)

sealed class LoadingState {
    object None : LoadingState()
    object LoggingOut : LoadingState()
    object DeletingAccount : LoadingState()
    object SendingPasswordOtp : LoadingState()
    object UpdatingName : LoadingState()
}
