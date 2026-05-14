package com.tomildev.trakii.features.settings.sub_settings.language.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.cards.HabitiiCard
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.presentation.components.topbars.BackbuttonTitleTopBar
import com.tomildev.trakii.features.settings.common.presentation.components.setting_options.SettingsItems
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun LanguageScreen(
    modifier: Modifier = Modifier,
    languageViewModel: LanguageViewModel = hiltViewModel(),
    onNavigateToMainSettings: () -> Unit,
) {
    val uiState by languageViewModel.uiState.collectAsStateWithLifecycle()

    val languageOptions = listOf(
        "es" to R.string.sub_settings_language_spanish,
        "en" to R.string.sub_settings_language_english
    )

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_language_title),
                backButton = { onNavigateToMainSettings() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = Dimens.ScreenHorizontalPadding,
                    vertical = Dimens.ScreenPaddingTop
                )
                .verticalScroll(rememberScrollState())
        ) {
            Texts.TitleSmall(
                text = stringResource(R.string.sub_settings_language_subtitle),
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingMedium)

            HabitiiCard {
                languageOptions.forEachIndexed { index, (langKey, stringRes) ->
                    SettingsItems.SettingsSelectionItem(
                        text = stringResource(stringRes),
                        selected = uiState.selectedLanguage == langKey,
                        showDivider = index != languageOptions.lastIndex,
                        onClick = {
                            languageViewModel.onEvent(LanguageUiEvent.SelectLanguage(langKey))
                        }
                    )
                }
            }
        }
    }
}