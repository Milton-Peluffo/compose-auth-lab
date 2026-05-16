package com.tomildev.trakii.features.settings.sub_settings.about.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
fun AboutAppScreen(
    modifier: Modifier = Modifier,
    aboutAppViewModel: AboutAppViewModel = hiltViewModel(),
    onNavigateToMainSettings: () -> Unit,
) {
    val uiState by aboutAppViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_about_title),
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Texts.TitleSmall(
                    text = stringResource(R.string.sub_settings_about_subtitle),
                    isSecondary = true
                )
                VerticalSpacer(Dimens.SpacingMedium)

                HabitiiCard {
                    SettingsItems.SettingsNavigationItem(
                        leadingIcon = R.drawable.ic_info_outlined,
                        text = stringResource(R.string.sub_settings_about_terms),
                        onClick = { aboutAppViewModel.onEvent(AboutAppUiEvent.OpenTerms) }
                    )
                    SettingsItems.SettingsNavigationItem(
                        leadingIcon = R.drawable.ic_lock_outlined,
                        text = stringResource(R.string.sub_settings_about_privacy),
                        onClick = { aboutAppViewModel.onEvent(AboutAppUiEvent.OpenPrivacy) }
                    )
                    SettingsItems.SettingsNavigationItem(
                        leadingIcon = R.drawable.ic_email_outlined,
                        text = stringResource(R.string.sub_settings_about_report_bug),
                        showDivider = false,
                        onClick = { aboutAppViewModel.onEvent(AboutAppUiEvent.ReportBug) }
                    )
                }

                VerticalSpacer(Dimens.SpacingLarge)

                HabitiiCard {
                    SettingsItems.SettingStaticItem(
                        text = stringResource(R.string.sub_settings_about_version),
                        supportingText = uiState.appVersion,
                        showDivider = false
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.SpacingLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Texts.LabelMedium(
                    text = stringResource(R.string.sub_settings_about_made_with_love),
                    isSecondary = true,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
