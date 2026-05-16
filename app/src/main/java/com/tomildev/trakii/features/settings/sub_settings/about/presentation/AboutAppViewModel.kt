package com.tomildev.trakii.features.settings.sub_settings.about.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AboutAppViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AboutAppUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AboutAppUiEvent) {
        when (event) {
            AboutAppUiEvent.OpenTerms -> {
                // TODO: Implement open terms logic (e.g., open URL)
            }
            AboutAppUiEvent.OpenPrivacy -> {
                // TODO: Implement open privacy logic
            }
            AboutAppUiEvent.ReportBug -> {
                // TODO: Implement report bug logic (e.g., send email)
            }
        }
    }
}
