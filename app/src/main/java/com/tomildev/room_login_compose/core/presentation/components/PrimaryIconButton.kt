package com.tomildev.room_login_compose.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryIconButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {

    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        onClick = { onClick() },
        enabled = enabled,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}