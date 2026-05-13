package com.tomildev.trakii.core.common.presentation.components.dividers

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

object Dividers {

    @Composable
    fun VerticalDivider(modifier: Modifier = Modifier) {

        VerticalDivider(
            modifier = modifier
                .fillMaxHeight(),
            thickness = Dimens.BorderTiny,
            color = MaterialTheme.colorScheme.outline.copy(alpha = Alpha.Overlay)
        )
    }

    @Composable
    fun HorizontalDivider(modifier: Modifier = Modifier) {
        HorizontalDivider(
            modifier = modifier
                .fillMaxWidth(),
            thickness = Dimens.BorderTiny,
            color = MaterialTheme.colorScheme.outline.copy(alpha = Alpha.Overlay)
        )
    }
}