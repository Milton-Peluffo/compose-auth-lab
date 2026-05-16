package com.tomildev.trakii.features.settings.sub_settings.about.presentation

sealed class AboutAppUiEvent {
    data object OpenTerms : AboutAppUiEvent()
    data object OpenPrivacy : AboutAppUiEvent()
    data object ReportBug : AboutAppUiEvent()
}
