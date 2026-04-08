package com.tomildev.room_login_compose.features.auth.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tomildev.room_login_compose.core.common.presentation.components.texts.Texts

@Composable
fun AuthHorizontalDivider(
    modifier: Modifier = Modifier,
    text: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalDivider(
            modifier = modifier.weight(1f),
            thickness = 2.2.dp,
            color = MaterialTheme.colorScheme.surface
        )
        Texts.Body(
            modifier = Modifier.weight(0.3f),
            text = text,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = modifier.weight(1f),
            thickness = 2.2.dp,
            color = MaterialTheme.colorScheme.surface
        )
    }
}