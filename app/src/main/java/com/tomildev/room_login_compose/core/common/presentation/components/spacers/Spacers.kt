package com.tomildev.room_login_compose.core.common.presentation.components.spacers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.tomildev.room_login_compose.ui.theme.Dimens

@Composable
fun VerticalSpacer(height: Dp = Dimens.SpacingMedium) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun HorizontalSpacer(width: Dp = Dimens.SpacingMedium) {
    Spacer(modifier = Modifier.width(width))
}