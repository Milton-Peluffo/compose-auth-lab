package com.tomildev.room_login_compose.features.auth.otp.presentation.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.tomildev.room_login_compose.core.common.presentation.components.texts.Texts
import com.tomildev.room_login_compose.ui.theme.Dimens

/**
 * A composable representing a single key in a numeric keypad, used for OTP feature.
 * It can display either a text value or an icon and supports both click and long-click actions.
 *
 * @param modifier The [Modifier] to be applied to the key's layout.
 * @param text The string to be displayed inside the key (e.g., "1", "2").
 * @param icon An optional [Painter] to display an icon instead of text (e.g., a backspace icon).
 * @param onClick Callback to be executed when the key is pressed.
 * @param onLongClick Optional callback to be executed when the key is long-pressed.
 */
@Composable
fun NumericKey(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: Painter? = null,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .size(Dimens.KeySize)
            .clip(CircleShape)
            .then(
                if (text != "" || icon != null) {
                    Modifier.combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            color = Color.LightGray,
                            bounded = true
                        )
                    )
                } else Modifier
            ), contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(Dimens.IconSizeMedium)
            )
        } else {
            Texts.Headline(text = text)
        }
    }
}