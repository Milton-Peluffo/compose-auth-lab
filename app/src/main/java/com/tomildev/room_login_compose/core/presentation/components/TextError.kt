package com.tomildev.room_login_compose.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TextError(modifier: Modifier = Modifier, text: String) {

    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.error
    )

}