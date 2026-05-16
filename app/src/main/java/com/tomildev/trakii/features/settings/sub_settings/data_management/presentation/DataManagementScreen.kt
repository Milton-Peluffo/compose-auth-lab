package com.tomildev.trakii.features.settings.sub_settings.data_management.presentation

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
fun DataManagementScreen(
    modifier: Modifier = Modifier,
    dataManagementViewModel: DataManagementViewModel = hiltViewModel(),
    onNavigateToMainSettings: () -> Unit,
) {
    val uiState by dataManagementViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_data_management_title),
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
                .verticalScroll(rememberScrollState())
        ) {
            Texts.TitleSmall(
                text = stringResource(R.string.sub_settings_data_management_subtitle),
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingMedium)

            HabitiiCard {
                SettingsItems.SettingActionItem(
                    text = stringResource(R.string.sub_settings_data_management_sync_status),
                    supportingText = uiState.syncStatus,
                    showDivider = true,
                    onClick = { }
                )
                SettingsItems.SettingActionItem(
                    leadingIcon = R.drawable.ic_cloud_download,
                    text = stringResource(R.string.sub_settings_data_management_download_cloud),
                    showDivider = true,
                    onClick = { dataManagementViewModel.onEvent(DataManagementUiEvent.DownloadData) }
                )
                SettingsItems.SettingActionItem(
                    leadingIcon = R.drawable.ic_cloud_upload,
                    text = stringResource(R.string.sub_settings_data_management_sync_cloud),
                    showDivider = false,
                    onClick = { dataManagementViewModel.onEvent(DataManagementUiEvent.SyncData) }
                )
            }

            VerticalSpacer(Dimens.SpacingLarge)

            Texts.TitleSmall(
                text = stringResource(R.string.sub_settings_data_management_danger_zone),
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingMedium)

            HabitiiCard {
                SettingsItems.SettingActionItem(
                    leadingIcon = R.drawable.ic_trash_outlined,
                    text = stringResource(R.string.sub_settings_data_management_clear_habits),
                    isWarning = true,
                    showDivider = true,
                    onClick = { dataManagementViewModel.onEvent(DataManagementUiEvent.ClearHabits) }
                )
                SettingsItems.SettingActionItem(
                    leadingIcon = R.drawable.ic_user_off,
                    text = stringResource(R.string.sub_settings_data_management_delete_account),
                    isWarning = true,
                    showDivider = false,
                    onClick = { dataManagementViewModel.onEvent(DataManagementUiEvent.DeleteAccount) }
                )
            }
        }
    }
}
