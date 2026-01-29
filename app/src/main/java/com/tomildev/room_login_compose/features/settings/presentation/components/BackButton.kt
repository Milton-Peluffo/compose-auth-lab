package com.tomildev.room_login_compose.features.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomildev.room_login_compose.R

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = "Go back"
        )
    }
}