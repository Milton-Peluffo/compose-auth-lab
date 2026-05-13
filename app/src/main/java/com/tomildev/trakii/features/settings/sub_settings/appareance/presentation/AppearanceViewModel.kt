package com.tomildev.trakii.features.settings.sub_settings.appareance.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    val uiState = appPreferences.appearance.map {
        AppearanceUiState(selectedTheme = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppearanceUiState()
    )

    fun onEvent(event: AppearanceUiEvent) {
        when (event) {
            is AppearanceUiEvent.SelectTheme -> {
                viewModelScope.launch {
                    appPreferences.setAppearance(event.theme)
                }
            }
            else -> { }
        }
    }
}