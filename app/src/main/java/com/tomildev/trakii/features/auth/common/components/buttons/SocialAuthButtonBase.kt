package com.tomildev.trakii.features.auth.common.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun SocialAuthButtonBase(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    icon: Painter,
    isLoading: Boolean = false
) {

    val alpha = Alpha.Secondary

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.ButtonHeight),
        onClick = { onClick() },
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = alpha),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
        )
    ) {
        Icon(painter = icon, contentDescription = null, tint = Color.Unspecified)
        HorizontalSpacer(Dimens.SpacingMedium)
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimens.IconSizeNormal),
                color = MaterialTheme.colorScheme.onSurface,
                strokeWidth = Dimens.BorderNormal
            )
        } else {
            Texts.LabelLarge(text = text)
        }
    }
}