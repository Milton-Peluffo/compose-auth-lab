package com.tomildev.trakii.features.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun SettingsItemContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                Dimens.BorderSmall,
                color = MaterialTheme.colorScheme.outline.copy(alpha = Alpha.Overlay),
                shape = MaterialTheme.shapes.medium
            )
    ) {
        content()
    }
}