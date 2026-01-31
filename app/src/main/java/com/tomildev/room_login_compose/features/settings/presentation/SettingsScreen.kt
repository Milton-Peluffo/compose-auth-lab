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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.presentation.components.PrimarySubtitle
import com.tomildev.room_login_compose.core.presentation.components.SecondaryTitle
import com.tomildev.room_login_compose.features.settings.presentation.components.BackButton
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsActionItem
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsItemContainer
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsNavigationItem
import com.tomildev.room_login_compose.features.settings.presentation.components.SettingsToggleItem
import com.tomildev.room_login_compose.features.settings.presentation.components.UserProfileHeader

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {

    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 15.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
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
                        onClick = {}
                    )
                    SettingsNavigationItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_lock,
                        text = "Password",
                        contentDescription = "Password",
                        onClick = {}
                    )
                    SettingsToggleItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_moon,
                        text = "Dark mode",
                        checked = true,
                        onCheckedChange = {},
                    )
                    SettingsActionItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_log_out,
                        text = "Log out",
                        showDivider = false,
                        onClick = {}
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            SettingsItemContainer(
                content = {
                    SettingsActionItem(
                        leadingIcon = com.tomildev.room_login_compose.R.drawable.ic_bin,
                        text = "Delete my account",
                        isWarning = true,
                        showDivider = false,
                        onClick = {}
                    )
                }
            )
        }
    }
}