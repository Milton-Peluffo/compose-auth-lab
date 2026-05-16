package com.tomildev.trakii.features.settings.main_settings.presentation

sealed class MainSettingsUiEvent {
    data object NavigateToSignIn : MainSettingsUiEvent()
    data object NavigateToHabitList : MainSettingsUiEvent()
    data object NavigateToAccount : MainSettingsUiEvent()
    data object NavigateToAppearance : MainSettingsUiEvent()
    data object NavigateToNotifications : MainSettingsUiEvent()
    data object NavigateToLanguage : MainSettingsUiEvent()
    data object NavigateToDataManagement : MainSettingsUiEvent()
    data object NavigateToAboutApp : MainSettingsUiEvent()
}