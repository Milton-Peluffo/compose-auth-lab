package com.tomildev.trakii.features.settings.main_settings.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tomildev.trakii.R
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

object SettingsItems {

    @Composable
    fun SettingsNavigationItem(
        leadingIcon: Int,
        text: String,
        supportingText: String? = null,
        isWarning: Boolean = false,
        showDivider: Boolean = true,
        onClick: () -> Unit
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            supportingText = supportingText,
            onClick = onClick,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = { color ->
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = color.copy(alpha = if (isWarning) Alpha.Full else Alpha.Overlay)
                )
            }
        )
    }

    @Composable
    fun SettingActionItem(
        leadingIcon: Int? = null,
        text: String,
        supportingText: String? = null,
        isWarning: Boolean = false,
        showDivider: Boolean = true,
        onClick: () -> Unit
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            supportingText = supportingText,
            onClick = onClick,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = null
        )
    }
    @Composable
    fun SettingStaticItem(
        leadingIcon: Int? = null,
        text: String,
        supportingText: String? = null,
        isWarning: Boolean = false,
        showDivider: Boolean = true,
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            supportingText = supportingText,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = null
        )
    }

    @Composable
    fun SettingsTwoLineItem(
        leadingIcon: Int,
        title: String,
        subtitle: String,
        isWarning: Boolean = false,
        showDivider: Boolean = true,
        onClick: (() -> Unit)? = null
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = title,
            supportingText = subtitle,
            onClick = onClick,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = if (onClick != null) {
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = it.copy(alpha = Alpha.Overlay)
                    )
                }
            } else {
                null
            }
        )
    }


    @Composable
    fun SettingsLoadingActionItem(
        leadingIcon: Int,
        text: String,
        supportingText: String? = null,
        isWarning: Boolean = false,
        isLoading: Boolean = false,
        showDivider: Boolean = true,
        onClick: () -> Unit
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            supportingText = supportingText,
            onClick = onClick,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = { color ->
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(Dimens.IconSizeNormal),
                        color = color,
                        strokeWidth = Dimens.BorderNormal

                    )
                }
            }
        )
    }

    @Composable
    fun SettingsToggleItem(
        leadingIcon: Int,
        text: String,
        supportingText: String? = null,
        showDivider: Boolean = true,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            supportingText = supportingText,
            showDivider = showDivider,
            onClick = { onCheckedChange(!checked) },
            trailingContent = {
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimary,
                        checkedBorderColor = MaterialTheme.colorScheme.outline,

                        uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedIconColor = MaterialTheme.colorScheme.outline,
                    )
                )
            }
        )
    }
}
