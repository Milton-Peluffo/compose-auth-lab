package com.tomildev.trakii.features.settings.subsettings.account.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.tomildev.trakii.core.common.presentation.components.dialogs.Dialogs
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.presentation.components.topbars.BackbuttonTitleTopBar
import com.tomildev.trakii.features.settings.main_settings.presentation.components.SettingsItemContainer
import com.tomildev.trakii.features.settings.main_settings.presentation.components.SettingsItems

@Composable
fun AccountSettingsScreen(
    modifier: Modifier = Modifier,
    accountSettingsViewModel: AccountSettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by accountSettingsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_account_title),
                backButton = { onNavigateBack() }
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

            Texts.Headline(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sub_settings_account_greeting)
            )
            Texts.Headline(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.name
            )
            Spacer(modifier = Modifier.height(8.dp))
            Texts.Body(
                text = stringResource(R.string.sub_settings_account_description),
                isSecondary = true
            )

            Spacer(modifier = Modifier.height(34.dp))
            Texts.TitleSmall(
                text = stringResource(R.string.sub_settings_account_section)
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsTwoLineItem(
                    leadingIcon = R.drawable.ic_email_outlined,
                    title = stringResource(R.string.sub_settings_account_email),
                    subtitle = uiState.email,
                    onClick = {}
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_lock_outlined,
                    text = stringResource(R.string.sub_settings_account_password),
                    onClick = {},
                )
                SettingsItems.SettingsTwoLineItem(
                    leadingIcon = R.drawable.ic_calendar_outlined,
                    title = stringResource(R.string.sub_settings_account_member_since),
                    subtitle = uiState.accountCreationDate,
                    showDivider = false
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Texts.TitleSmall(
                text = stringResource(R.string.sub_settings_account_actions_subtitle),
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsLoadingActionItem(
                    leadingIcon = R.drawable.ic_logout_outlined,
                    text = stringResource(R.string.sub_settings_account_logout),
                    isLoading = uiState.loadingState is LoadingState.LoggingOut,
                    showDivider = false,
                    onClick = { accountSettingsViewModel.onLogoutClick() }
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_alert_outlined,
                    text = stringResource(R.string.sub_settings_account_danger_zone),
                    onClick = {},
                    isWarning = true,
                    showDivider = false
                )
            }
        }

        if (uiState.showLogoutDialog) {
            Dialogs.LogOut(
                onConfirm = { accountSettingsViewModel.onConfirmLogoutDialog() },
                onDismiss = { accountSettingsViewModel.onDismissLogoutDialog() }
            )
        }
    }
}
