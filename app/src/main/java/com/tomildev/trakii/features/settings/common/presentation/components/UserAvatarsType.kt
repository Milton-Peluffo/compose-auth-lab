package com.tomildev.trakii.features.settings.common.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object UserAvatarsType {
    @Composable
    fun MainSettingsUserAvatar(
        avatarUrl: String
    ) {
        val avatarSize: Dp = 46.dp
        UserPictureBase(
            modifier = Modifier
                .size(avatarSize)
                .clip(shape = MaterialTheme.shapes.extraLarge),
            avatarUrl = avatarUrl
        )
    }

    @Composable
    fun AccountSettingsUserAvatar(
        avatarUrl: String
    ) {
        val avatarSize: Dp = 95.dp
        UserPictureBase(
            modifier = Modifier
                .size(avatarSize)
                .clip(shape = MaterialTheme.shapes.extraLarge),
            avatarUrl = avatarUrl
        )
    }
}