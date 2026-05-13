package com.tomildev.trakii.features.settings.sub_settings.notifications.presentation

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
import com.tomildev.trakii.features.settings.sub_settings.appareance.presentation.AppearanceUiEvent
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    notificationsViewModel: NotificationsViewModel = hiltViewModel(),
    onNavigationEvent: (AppearanceUiEvent) -> Unit
) {
    val uiState by notificationsViewModel.uiState.collectAsStateWithLifecycle()

    val notificationsOptions = listOf(
        Triple(
            "all",
            R.string.sub_settings_notifications_all_notifications,
            R.drawable.ic_bell_outlined,
        )
    )

    Scaffold(
        topBar = {
            BackbuttonTitleTopBar(
                title = stringResource(R.string.sub_settings_notifications_title),
                backButton = { onNavigationEvent(AppearanceUiEvent.NavigateToMainSettings) }
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
                text = stringResource(R.string.sub_settings_notifications_subtitle),
                isSecondary = true
            )
            VerticalSpacer(Dimens.SpacingMedium)
            HabitiiCard {
                notificationsOptions.forEachIndexed { index, (notificationKey, stringRes, iconRes) ->
                    SettingsItems.SettingsToggleItem(
                        leadingIcon = iconRes,
                        text = stringResource(stringRes),
                        checked = uiState.activeNotifications == notificationKey,
                        showDivider = index != notificationsOptions.lastIndex,
                        onCheckedChange = { }
                    )
                }
            }
        }
    }
}