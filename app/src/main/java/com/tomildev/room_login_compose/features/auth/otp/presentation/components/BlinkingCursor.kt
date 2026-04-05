package com.tomildev.room_login_compose.features.auth.otp.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * A composable that renders a vertical blinking cursor effect.
 *
 * This component uses an infinite transition to animate the opacity of a thin vertical bar,
 * simulating the standard text insertion cursor. Specifically created for digit entry on
 * the OTP entry screen
 */
@Composable
fun BlinkingCursor() {

    val infiniteTransition = rememberInfiniteTransition(label = "cursor")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1400
                1f at 0
                1f at 500

                0f at 1000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .size(width = 3.dp,height = 28.dp)
            .graphicsLayer(alpha = alpha)
            .background(MaterialTheme.colorScheme.outline)
    )
}