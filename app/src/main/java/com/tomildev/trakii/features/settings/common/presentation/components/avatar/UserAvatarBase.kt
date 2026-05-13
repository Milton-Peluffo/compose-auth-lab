package com.tomildev.trakii.features.settings.common.presentation.components.avatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun UserPictureBase(
    modifier: Modifier = Modifier,
    avatarUrl: String
) {
    AsyncImage(
        modifier = modifier,
        model = avatarUrl,
        contentDescription = null,
    )
}