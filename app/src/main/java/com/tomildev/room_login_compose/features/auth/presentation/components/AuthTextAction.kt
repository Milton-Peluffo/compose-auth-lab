package com.tomildev.room_login_compose.features.auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AuthTextAction(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }),
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}