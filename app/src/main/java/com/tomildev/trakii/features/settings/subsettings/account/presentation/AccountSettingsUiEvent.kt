package com.tomildev.trakii.features.settings.subsettings.account.presentation

sealed class AccountSettingsUiEvent {
    data object NavigateToSignIn : AccountSettingsUiEvent()
}