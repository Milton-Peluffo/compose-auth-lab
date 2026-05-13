package com.tomildev.trakii.features.settings.common.presentation.components.setting_options

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun SettingsItemBase(
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    text: String,
    supportingText: String? = null,
    contentDescription: String? = null,
    isWarning: Boolean = false,
    showDivider: Boolean = true,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable ((color: Color) -> Unit)? = null
) {
    val color = if (isWarning) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val supportingColor = if (isWarning) {
        MaterialTheme.colorScheme.error.copy(alpha = Alpha.Secondary)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Alpha.Secondary)
    }

    val rippleColor = if (isWarning) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = if (supportingText == null) 56.dp else 64.dp)
                .let {
                    if (onClick != null) {
                        it.clickable(
                            onClick = onClick,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = rippleColor)
                        )
                    } else it
                }
                .padding(horizontal = Dimens.SpacingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Icon(
                    modifier = Modifier.size(Dimens.IconSizeNormal),
                    painter = painterResource(leadingIcon),
                    contentDescription = contentDescription,
                    tint = color
                )
                HorizontalSpacer(Dimens.SpacingMedium)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                Texts.Body(
                    text = text,
                    color = color,
                )
                supportingText?.let {
                    Texts.LabelMedium(
                        text = supportingText,
                        color = color,
                        isSecondary = true
                    )
                }
            }
            trailingContent?.invoke(color)
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = Dimens.BorderTiny,
                color = MaterialTheme.colorScheme.outline.copy(alpha = Alpha.Overlay)
            )
        }
    }
}