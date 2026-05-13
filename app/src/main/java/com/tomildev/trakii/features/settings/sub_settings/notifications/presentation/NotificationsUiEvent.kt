package com.tomildev.trakii.features.settings.sub_settings.notifications.presentation

sealed class NotificationsUiEvent {
    data object NavigateToMainSettings : NotificationsUiEvent()
}