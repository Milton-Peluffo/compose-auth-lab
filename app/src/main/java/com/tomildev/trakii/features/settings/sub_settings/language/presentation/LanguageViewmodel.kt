package com.tomildev.trakii.features.settings.sub_settings.language.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    val uiState = appPreferences.selectedLanguage.map {
        LanguageUiState(selectedLanguage = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LanguageUiState()
    )

    fun onEvent(event: LanguageUiEvent) {
        when (event) {
            is LanguageUiEvent.SelectLanguage -> {
                viewModelScope.launch {
                    appPreferences.saveLanguage(event.langCode)
                }
            }
            else -> Unit
        }
    }
}