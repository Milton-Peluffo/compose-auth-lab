package com.tomildev.trakii.features.settings.main_settings.presentation

sealed class SettingsUiEvent {
    data object NavigateToSignIn : SettingsUiEvent()
    data object NavigateToHabitList : SettingsUiEvent()
    data object NavigateToAccount : SettingsUiEvent()
    data object NavigateToAppearance : SettingsUiEvent()
    data object NavigateToNotifications : SettingsUiEvent()
    data object NavigateToLanguage : SettingsUiEvent()
    data object NavigateToDatacontrols : SettingsUiEvent()
    data object NavigateToAboutApp : SettingsUiEvent()
}