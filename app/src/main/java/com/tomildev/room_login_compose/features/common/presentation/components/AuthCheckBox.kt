package com.tomildev.room_login_compose.features.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AuthCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    text: String
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onCheckedChange(!checked) },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            modifier = modifier,
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
//        colors = CheckboxDefaults.colors(
//            checkedColor = MaterialTheme.colorScheme.primary,
//            uncheckedColor = MaterialTheme.colorScheme.onPrimary
//        )
        )
        Text(
            modifier = modifier
                .fillMaxWidth(),
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}