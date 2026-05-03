package com.tomildev.trakii.features.settings.presentation.components

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
        isWarning: Boolean = false,
        showDivider: Boolean = true,
        onClick: () -> Unit
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            onClick = onClick,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Alpha.Overlay)
                )
            }
        )
    }

    @Composable
    fun SettingsActionItem(
        leadingIcon: Int,
        text: String,
        isWarning: Boolean = false,
        showDivider: Boolean = true,
        onClick: () -> Unit
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
            onClick = onClick,
            isWarning = isWarning,
            showDivider = showDivider,
            trailingContent = null
        )
    }

    @Composable
    fun SettingsLoadingActionItem(
        leadingIcon: Int,
        text: String,
        isWarning: Boolean = false,
        isLoading: Boolean = false,
        showDivider: Boolean = true,
        onClick: () -> Unit
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
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
        showDivider: Boolean = true,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
    ) {
        SettingsItemBase(
            leadingIcon = leadingIcon,
            text = text,
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