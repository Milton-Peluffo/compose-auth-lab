package com.tomildev.trakii.features.settings.subsettings.account.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

            Texts.Headline(modifier = Modifier, text = uiState.name)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsStaticItem(
                    leadingIcon = R.drawable.ic_email_outlined,
                    text = uiState.email,
                    onClick = {}
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_lock_outlined,
                    text = "Password",
                    onClick = {},
                )
                SettingsItems.SettingsStaticItem(
                    text = "${stringResource(R.string.sub_settings_account_member_since)} ${uiState.accountCreationDate}",
                    onClick = {}
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Texts.TitleSmall(
                text = stringResource(R.string.settings_app_preferences),
                isSecondary = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsLoadingActionItem(
                    leadingIcon = R.drawable.ic_logout_outlined,
                    text = "Log Out",
                    isLoading = uiState.loadingState is LoadingState.LoggingOut,
                    onClick = { accountSettingsViewModel.onLogoutClick() }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_alert_outlined,
                    text = "Danger Zone",
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