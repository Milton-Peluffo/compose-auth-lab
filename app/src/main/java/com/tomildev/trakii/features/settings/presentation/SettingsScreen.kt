@file:OptIn(ExperimentalMaterial3Api::class)

package com.tomildev.trakii.features.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.presentation.components.topbars.BackbuttonTitleTopBar
import com.tomildev.trakii.features.settings.presentation.components.SettingsItemContainer
import com.tomildev.trakii.features.settings.presentation.components.SettingsItems
import com.tomildev.trakii.features.settings.presentation.components.UserProfileHeader

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToHabitList: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(Unit) {
        settingsViewModel.events.collect { event ->
            when (event) {
                is SettingsUiEvent.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.settings_title), // "Settings"
                backButton = { onNavigateToHabitList() }
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
            UserProfileHeader(
                modifier = Modifier.fillMaxWidth(),
                userName = uiState.name,
                userEmail = uiState.email,
                onclick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            Texts.TitleSmall(
                text = stringResource(R.string.settings_app_preferences),
                isSecondary = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_moon_outlined,
                    text = stringResource(R.string.settings_other_theme),
                    onClick = { }
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_bell_outlined,
                    text = stringResource(R.string.settings_other_notifications),
                    onClick = { }
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_language_outlined,
                    text = stringResource(R.string.settings_other_language),
                    onClick = { }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Texts.TitleSmall(
                text = stringResource(R.string.settings_other_subtitle),
                isSecondary = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsItemContainer {
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_database_outlined,
                    text = stringResource(R.string.settings_other_data_controls),
                    onClick = {}
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_info_outlined,
                    text = "Acerca de",
                    onClick = {}
                )
                SettingsItems.SettingsNavigationItem(
                    leadingIcon = R.drawable.ic_star_outlined,
                    text = "Rate Trakii",
                    showDivider = false,
                    onClick = { }
                )
            }


//            Texts.TitleSmall(
//                text = "Account Actions",
//                isSecondary = true
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            SettingsItemContainer {
//                SettingsItems.SettingsLoadingActionItem(
//                    leadingIcon = R.drawable.ic_log_out,
//                    text = "Log out",
//                    isLoading = uiState.loadingState is LoadingState.LoggingOut,
//                    onClick = { settingsViewModel.onLogoutClick() }
//                )
//                SettingsItems.SettingsLoadingActionItem(
//                    leadingIcon = R.drawable.ic_bin,
//                    text = "Delete my account",
//                    isWarning = true,
//                    showDivider = false,
//                    isLoading = uiState.loadingState is LoadingState.DeletingAccount,
//                    onClick = { settingsViewModel.onDeleteAccountClick() }
//                )
//            }

        }

//    if (uiState.showLogoutDialog) {
//        Dialogs.LogOut(
//            onConfirm = { settingsViewModel.onConfirmLogoutDialog() },
//            onDismiss = { settingsViewModel.onDismissLogoutDialog() }
//        )
//    }
//
//    if (uiState.showDeleteAccountDialog) {
//        Dialogs.DeleteAccount(
//            onConfirm = { settingsViewModel.onConfirmDeleteAccountDialog() },
//            onDismiss = { settingsViewModel.onDismissDeleteAccountDialog() }
//        )
//    }
    }
}