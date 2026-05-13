package com.tomildev.trakii.features.settings.sub_settings.appareance.presentation

sealed class AppearanceUiEvent {
    data object NavigateToMainSettings : AppearanceUiEvent()
    data class SelectTheme(val theme: String) : AppearanceUiEvent()
}