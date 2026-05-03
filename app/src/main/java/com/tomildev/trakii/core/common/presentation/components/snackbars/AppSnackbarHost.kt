package com.tomildev.trakii.core.common.presentation.components.snackbars

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.unit.IntOffset
import com.tomildev.trakii.ui.theme.Dimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val accessibilityManager = LocalAccessibilityManager.current
    val scope = rememberCoroutineScope()

    val offsetX = remember { Animatable(0f) }
    var width by remember { mutableIntStateOf(0) }

    val currentData = hostState.currentSnackbarData
    val data = remember(currentData) { currentData }

    suspend fun dismissWithAnimation(snackbar: SnackbarData) {
        val start = width + 200f

        offsetX.stop()

        offsetX.animateTo(
            targetValue = 60f,
            animationSpec = spring(
                dampingRatio = 0.9f,
                stiffness = Spring.StiffnessMedium
            )
        )

        offsetX.animateTo(
            targetValue = -start,
            animationSpec = spring(
                dampingRatio = 0.85f,
                stiffness = Spring.StiffnessLow
            )
        )

        snackbar.dismiss()
    }

    LaunchedEffect(data, width) {
        if (data == null || width == 0) return@LaunchedEffect

        val start = width + 200f

        offsetX.stop()
        offsetX.snapTo(start)

        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = 0.8f,
                stiffness = Spring.StiffnessLow
            )
        )

        val duration = data.visuals.duration.toMillis(
            hasAction = data.visuals.actionLabel != null,
            accessibilityManager = accessibilityManager
        )

        delay(duration)

        dismissWithAnimation(data)
    }

    Box(
        modifier = modifier
            .padding(vertical = Dimens.SnackbarBottomPadding)
            .onSizeChanged { width = it.width }
            .offset {
                IntOffset(
                    x = offsetX.value.roundToInt(),
                    y = 0
                )
            }
    ) {
        data?.let { snackbar ->
            SnackbarContent(
                data = snackbar,
                onDismiss = {
                    scope.launch {
                        dismissWithAnimation(snackbar)
                    }
                }
            )
        }
    }
}

@Composable
private fun SnackbarContent(
    data: SnackbarData,
    onDismiss: () -> Unit
) {
    val customVisuals = data.visuals as? SnackbarVisualsCustom

    if (customVisuals != null) {
        when (customVisuals.type) {
            SnackbarType.Error -> SnackBars.Error(
                title = customVisuals.message,
                description = customVisuals.description,
                onClick = onDismiss
            )

            SnackbarType.Success -> SnackBars.Success(
                title = customVisuals.message,
                description = customVisuals.description,
                onClick = onDismiss
            )

            SnackbarType.Warning -> SnackBars.Warning(
                title = customVisuals.message,
                description = customVisuals.description,
                onClick = onDismiss
            )

            SnackbarType.Info -> SnackBars.Info(
                title = customVisuals.message,
                description = customVisuals.description,
                onClick = onDismiss
            )
        }
    } else {
        Snackbar(snackbarData = data)
    }
}

private fun SnackbarDuration.toMillis(
    hasAction: Boolean,
    accessibilityManager: androidx.compose.ui.platform.AccessibilityManager?
): Long {
    val original = when (this) {
        SnackbarDuration.Indefinite -> Long.MAX_VALUE
        SnackbarDuration.Long -> 10000L
        SnackbarDuration.Short -> 4000L
    }

    if (accessibilityManager == null) return original

    return accessibilityManager.calculateRecommendedTimeoutMillis(
        original,
        containsIcons = true,
        containsText = true,
        containsControls = hasAction
    )
}