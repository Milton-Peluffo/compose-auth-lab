package com.tomildev.room_login_compose.features.auth.common.components.social

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
import androidx.compose.ui.unit.dp
import com.tomildev.room_login_compose.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.room_login_compose.core.common.presentation.components.texts.Texts
import com.tomildev.room_login_compose.ui.theme.Dimens

@Composable
fun SocialAuthButtonBase(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    icon: Painter,
    contentDescription: String? = null,
    isLoading: Boolean = false
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        onClick = { onClick() },
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    ) {
        Icon(painter = icon, contentDescription = contentDescription, tint = Color.Unspecified)
        HorizontalSpacer(Dimens.SpacingMedium)
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Texts.Label(text = text)
        }
    }
}