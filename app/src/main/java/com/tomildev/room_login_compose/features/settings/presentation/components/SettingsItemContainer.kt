package com.tomildev.room_login_compose.features.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomildev.room_login_compose.R

@Composable
fun SettingsItemContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        content()
    }
}

@Composable
private fun SettingsItemBase(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    text: String,
    contentDescription: String? = null,
    isWarning: Boolean = false,
    showDivider: Boolean = true,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {

    val color =
        if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
    val rippleColor =
        if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    Column() {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .let {
                    if (onClick != null) it.clickable(
                        onClick = onClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = rippleColor)
                    ) else it
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(leadingIcon),
                contentDescription = contentDescription,
                tint = color
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                textAlign = TextAlign.Start,
                color = color
            )
            trailingContent?.invoke()
        }
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun SettingsNavigationItem(
    leadingIcon: Int,
    text: String,
    contentDescription: String,
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
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
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