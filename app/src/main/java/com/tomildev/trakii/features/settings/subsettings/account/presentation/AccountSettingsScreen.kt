package com.tomildev.trakii.features.settings.subsettings.account.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.cards.HabitiiCard
import com.tomildev.trakii.core.common.presentation.components.snackbars.AppSnackbarHost
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.presentation.components.topbars.BackbuttonTitleTopBar
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.features.settings.common.presentation.components.UserAvatarsType
import com.tomildev.trakii.features.settings.main_settings.presentation.components.habitstats.HabitInsightsSection
import com.tomildev.trakii.features.settings.main_settings.presentation.components.habitstats.HabitStatsSection
import com.tomildev.trakii.features.settings.main_settings.presentation.components.setting_options.SettingsItems
import com.tomildev.trakii.features.settings.subsettings.account.presentation.components.AccountEditNameDialog
import com.tomildev.trakii.features.settings.subsettings.account.presentation.components.AccountLogoutDialog
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun AccountSettingsScreen(
    modifier: Modifier = Modifier,
    accountSettingsViewModel: AccountSettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by accountSettingsViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        accountSettingsViewModel.events.collect { event ->
            when (event) {
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserAvatarsType.AccountSettingsUserAvatar(
                avatarUrl = uiState.avatarUrl
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Texts.Headline(
                    text = uiState.name
                )
                IconButton(
                    onClick = { accountSettingsViewModel.onEditNameClick() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit_outlined),
                        contentDescription = stringResource(R.string.sub_settings_account_edit_name),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Texts.LabelMedium(
                modifier = Modifier,
                text = uiState.email,
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingMedium)
            HabitiiCard {
                SettingsItems.SettingsTwoLineItem(
                    leadingIcon = R.drawable.ic_calendar_outlined,
                    title = stringResource(R.string.sub_settings_account_member_since),
                    subtitle = uiState.accountCreationDate,
                )
            }
            VerticalSpacer(Dimens.SpacingMedium)
            HabitStatsSection(
                completed = "142",
                currentStreak = "28",
                successRate = "78%"
            )
            VerticalSpacer(Dimens.SpacingMedium)
            HabitInsightsSection(
                currentHabits = "6 habitos",
                timeInvested = "98 h",
                bestStreak = "28 dias"
            )
            VerticalSpacer(Dimens.SpacingMedium)
        }
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