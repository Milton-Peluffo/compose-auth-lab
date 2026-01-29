package com.tomildev.room_login_compose.core.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimarySubtitle(modifier: Modifier = Modifier, text: String) {

    Spacer(modifier = modifier.height(10.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onBackground
    )
}