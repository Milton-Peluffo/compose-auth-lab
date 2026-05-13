package com.tomildev.trakii.features.settings.sub_settings.appareance.presentation

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
import androidx.compose.ui.unit.dp
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
fun AppearanceScreen(
    modifier: Modifier = Modifier,
    appearanceViewModel: AppearanceViewModel = hiltViewModel(),
    onNavigationEvent: (AppearanceUiEvent) -> Unit
) {
    val uiState by appearanceViewModel.uiState.collectAsStateWithLifecycle()

    val themeOptions = listOf(
        Triple("dark", R.string.sub_settings_appearance_dark_mode, R.drawable.ic_moon_outlined),
        Triple("light", R.string.sub_settings_appearance_light_mode, R.drawable.ic_sun_outlined),
        Triple("system", R.string.sub_settings_appearance_system_mode, R.drawable.ic_device_outlined)
    )

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_appearance_title),
                backButton = { onNavigationEvent(AppearanceUiEvent.NavigateToMainSettings) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Texts.TitleSmall(
                text = "Selecciona el tema de la app",
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingMedium)
            HabitiiCard {
                themeOptions.forEachIndexed { index, (themeKey, stringRes, iconRes) ->
                    SettingsItems.SettingsSelectionItem(
                        leadingIcon = iconRes,
                        text = stringResource(stringRes),
                        selected = uiState.selectedTheme == themeKey,
                        showDivider = index != themeOptions.lastIndex,
                        onClick = {
                            appearanceViewModel.onEvent(
                                AppearanceUiEvent.SelectTheme(
                                    themeKey
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}