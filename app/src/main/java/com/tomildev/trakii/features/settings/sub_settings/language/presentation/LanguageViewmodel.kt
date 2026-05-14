package com.tomildev.trakii.features.settings.sub_settings.language.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LanguageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val currentLang = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        val langCode = currentLang.ifEmpty {
            java.util.Locale.getDefault().language
        }
        
        _uiState.update { it.copy(selectedLanguage = langCode) }
    }
    fun onEvent(event: LanguageUiEvent) {
        when (event) {
            is LanguageUiEvent.SelectLanguage -> {
                selectLanguage(event.langCode)
            }
            else -> Unit
        }
    }

    private fun selectLanguage(langCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(langCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
        _uiState.update { it.copy(selectedLanguage = langCode) }
    }
}
