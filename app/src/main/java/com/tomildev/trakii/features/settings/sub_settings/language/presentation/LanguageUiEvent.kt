package com.tomildev.trakii.features.settings.sub_settings.language.presentation

sealed interface LanguageUiEvent {
    data class SelectLanguage(val langCode: String) : LanguageUiEvent
    data object NavigateToMainSettings : LanguageUiEvent
}