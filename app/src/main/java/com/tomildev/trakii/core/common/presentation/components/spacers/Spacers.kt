package com.tomildev.trakii.core.common.presentation.components.spacers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun VerticalSpacer(height: Dp = Dimens.SpacingMedium) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun HorizontalSpacer(width: Dp = Dimens.SpacingMedium) {
    Spacer(modifier = Modifier.width(width))
}