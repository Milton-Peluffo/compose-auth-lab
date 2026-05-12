package com.tomildev.trakii.features.settings.subsettings.account.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.snackbars.AppSnackbarHost
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.presentation.components.topbars.BackbuttonTitleTopBar
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.features.settings.main_settings.presentation.components.SettingsItemContainer
import com.tomildev.trakii.features.settings.main_settings.presentation.components.SettingsItems
import com.tomildev.trakii.features.settings.subsettings.account.presentation.components.AccountEditNameDialog
import com.tomildev.trakii.features.settings.subsettings.account.presentation.components.AccountLogoutDialog

@Composable
fun AccountSettingsScreen(
    modifier: Modifier = Modifier,
    accountSettingsViewModel: AccountSettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val uiState by accountSettingsViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        accountSettingsViewModel.events.collect { event ->
            when (event) {
                AccountSettingsUiEvent.NavigateToSignIn -> onNavigateToSignIn()
                is AccountSettingsUiEvent.Error -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Error
                        )
                    )
                }

                is AccountSettingsUiEvent.Warning -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Warning
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_account_title),
                backButton = { onNavigateBack() }
            )
        },
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Texts.Headline(
                    modifier = Modifier.weight(1f),
                    text = uiState.name
                )
                IconButton(onClick = { accountSettingsViewModel.onEditNameClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit_outlined),
                        contentDescription = stringResource(R.string.sub_settings_account_edit_name),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
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
            AccountLogoutDialog(
                onConfirm = { accountSettingsViewModel.onConfirmLogoutDialog() },
                onDismiss = { accountSettingsViewModel.onDismissLogoutDialog() }
            )
        }

        if (uiState.showEditNameDialog) {
            AccountEditNameDialog(
                editedName = uiState.editedName,
                nameError = uiState.nameError,
                onNameChange = { accountSettingsViewModel.onEditedNameChange(it) },
                onConfirm = { accountSettingsViewModel.onConfirmEditNameDialog() },
                onDismiss = { accountSettingsViewModel.onDismissEditNameDialog() }
            )
        }
    }
}
