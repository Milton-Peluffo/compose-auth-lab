package com.tomildev.room_login_compose.features.settings.presentation.components

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsItemContainer(
    modifier: Modifier = Modifier,
    settingsItem: @Composable () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Column(
//            modifier = Modifier.background(Color.Blue),
        ) {
            settingsItem()
        }
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    text: String,
    trailingIcon: Int,
    contentDescription: String,
    isToggleable: Boolean = false,
    isToggleChecked: Boolean = false,
    isWarningColor: Boolean = false,
    hasTrailingIcon: Boolean = true,
    onToggleCheckedChange: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .padding(vertical = 2.dp, horizontal = 10.dp),
//            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(leadingIcon),
                contentDescription = contentDescription,
                tint = if (isWarningColor) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = text,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = if (isWarningColor) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                if (isToggleable) {
                    IconToggleButton(
                        modifier = Modifier.size(40.dp),
                        checked = isToggleChecked,
                        onCheckedChange = { onToggleCheckedChange() },
                        content = {}
                    )
                } else {
                    if (hasTrailingIcon) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(trailingIcon),
                            contentDescription = contentDescription,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}