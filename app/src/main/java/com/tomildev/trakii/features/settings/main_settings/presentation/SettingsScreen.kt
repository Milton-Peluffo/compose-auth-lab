@file:OptIn(ExperimentalMaterial3Api::class)

package com.tomildev.trakii.features.settings.main_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.tomildev.trakii.features.settings.main_settings.presentation.components.UserAccountHeader
import com.tomildev.trakii.features.settings.sub_settings.account.presentation.components.AccountLogoutDialog
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigationEvent: (SettingsUiEvent) -> Unit
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.main_settings_title),
                backButton = { onNavigationEvent(SettingsUiEvent.NavigateToHabitList) }
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
            UserAccountHeader(
                modifier = Modifier.fillMaxWidth(),
                userName = uiState.name,
                avatarUrl = uiState.avatarUrl,
                userEmail = uiState.email,
                onclick = { onNavigationEvent(SettingsUiEvent.NavigateToAccount) }
            )
            VerticalSpacer(Dimens.SpacingExtraLarge)
            Texts.TitleSmall(
                text = stringResource(R.string.main_settings_my_preferences_section),
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingSmall)
            HabitiiCard {
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_moon_outlined,
                    text = stringResource(R.string.main_settings_appearance),
                    onClick = { onNavigationEvent(SettingsUiEvent.NavigateToAppearance) }
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_bell_outlined,
                    text = stringResource(R.string.main_settings_notifications),
                    onClick = { onNavigationEvent(SettingsUiEvent.NavigateToNotifications) }
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_language_outlined,
                    text = stringResource(R.string.main_settings_language),
                    onClick = { },
                    showDivider = false
                )
            }
            VerticalSpacer(Dimens.SpacingSmall)
            Texts.TitleSmall(
                text = stringResource(R.string.main_settings_other_section),
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingSmall)
            HabitiiCard {
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_database_outlined,
                    text = stringResource(R.string.main_settings_data_controls),
                    onClick = {}
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_info_outlined,
                    text = stringResource(R.string.main_settings_about_habitii),
                    onClick = {}
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_star_outlined,
                    text = stringResource(R.string.main_settings_rate_app),
                    onClick = { }
                )
                SettingsItems.SettingsLoadingActionItem(
                    leadingIcon = R.drawable.ic_logout_outlined,
                    text = stringResource(R.string.main_settings_logout),
                    isLoading = uiState.loadingState == LoadingState.LoggingOut,
                    showDivider = false,
                    isWarning = true,
                    onClick = {
                        settingsViewModel.onLogoutClick()
                    }
                )
            }
        }
        if (uiState.showLogoutDialog) {
            AccountLogoutDialog(
                onConfirm = { settingsViewModel.onConfirmLogoutDialog() },
                onDismiss = { settingsViewModel.onDismissLogoutDialog() }
            )
        }
    }
}