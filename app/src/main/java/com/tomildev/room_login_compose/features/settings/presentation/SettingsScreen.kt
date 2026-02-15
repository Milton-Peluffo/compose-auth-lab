@file:OptIn(ExperimentalMaterial3Api::class)

package com.tomildev.room_login_compose.features.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.presentation.components.PrimarySubtitle
import com.tomildev.room_login_compose.core.presentation.components.SecondaryTitle
import com.tomildev.room_login_compose.core.presentation.components.dialogs.Dialogs
import com.tomildev.room_login_compose.features.settings.presentation.components.BackButton
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsItemContainer
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsLoadingActionItem
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsNavigationItem
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsToggleItem
import com.tomildev.room_login_compose.features.settings.presentation.components.UserProfileHeader

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToAccountInfo: () -> Unit,
    onNavigateToAccountPassword: () -> Unit
) {

    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(Unit) {
        settingsViewModel.events.collect { event ->
            when (event) {
                is SettingsUiEvent.NavigateToLogin ->
                    onNavigateToLogin()

                is SettingsUiEvent.NavigateToAccountInfo ->
                    onNavigateToAccountInfo

                is SettingsUiEvent.NavigateToAccountPassword ->
                    onNavigateToAccountPassword
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 15.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    scrolledContainerColor = androidx.compose.ui.graphics.Color.Transparent
                ),
                navigationIcon = { BackButton(onClick = { onNavigateToHome() }) },
                title = {
                    SecondaryTitle(title = "Settings")
                },
                windowInsets = TopAppBarDefaults.windowInsets
            )
        }

    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            UserProfileHeader(
                modifier = Modifier.fillMaxWidth(),
                userName = uiState.name,
                userEmail = uiState.email
            )
            Spacer(modifier = Modifier.height(20.dp))
            PrimarySubtitle(text = "Other settings")
            Spacer(modifier = Modifier.height(15.dp))
            SettingsItemContainer(
                content = {
                    SettingsNavigationItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_person,
                        text = "Profile details",
                        contentDescription = "Profile details",
                        onClick = { settingsViewModel.onAccountInfoClick() }
                    )
                    SettingsNavigationItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_lock,
                        text = "Password",
                        contentDescription = "Password",
                        onClick = { settingsViewModel.onAccountPasswordClick() }
                    )
                    SettingsToggleItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_moon,
                        text = "Dark mode",
                        checked = isDarkTheme,
                        onCheckedChange = { newValue -> settingsViewModel.onThemeChanged(newValue) },
                    )
                    SettingsLoadingActionItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_log_out,
                        text = "Log out",
                        showDivider = false,
                        isLoading = uiState.loadingState is LoadingState.LoggingOut,
                        onClick = {
                            settingsViewModel.onLogoutClick()
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            SettingsItemContainer(
                content = {
                    SettingsLoadingActionItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_bin,
                        text = "Delete my account",
                        isWarning = true,
                        showDivider = false,
                        isLoading = uiState.loadingState is LoadingState.DeletingAccount,
                        onClick = { settingsViewModel.onDeleteAccountClick() }
                    )
                }
            )
            if (uiState.showLogoutDialog) {
                Dialogs.LogOut(
                    onConfirm = { settingsViewModel.onConfirmLogoutDialog() },
                    onDismiss = { settingsViewModel.onDismissLogoutDialog() }
                )
            }

            if (uiState.showDeleteAccountDialog) {
                Dialogs.DeleteAccount(
                    onConfirm = { settingsViewModel.onConfirmDeleteAccountDialog() },
                    onDismiss = { settingsViewModel.onDismissDeleteAccountDialog() }
                )
            }
        }
    }
}