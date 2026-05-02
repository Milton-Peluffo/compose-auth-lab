package com.tomildev.trakii.core.common.presentation.components.snackbars

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        modifier = modifier.padding(vertical = Dimens.SnackbarBottomPadding),
        hostState = hostState
    ) { data ->
        val customVisuals = data.visuals as? SnackbarVisualsCustom

        if (customVisuals != null) {
            when (customVisuals.type) {
                SnackbarType.Error -> SnackBars.Error(
                    title = customVisuals.message,
                    description = customVisuals.description,
                    onClick = { data.dismiss() }
                )

                SnackbarType.Success -> SnackBars.Success(
                    title = customVisuals.message,
                    description = customVisuals.description,
                    onClick = { data.dismiss() }
                )

                SnackbarType.Warning -> SnackBars.Warning(
                    title = customVisuals.message,
                    description = customVisuals.description,
                    onClick = { data.dismiss() }
                )

                SnackbarType.Info -> SnackBars.Info(
                    title = customVisuals.message,
                    description = customVisuals.description,
                    onClick = { data.dismiss() }
                )
            }
        } else {
            Snackbar(snackbarData = data)
        }
    }
}