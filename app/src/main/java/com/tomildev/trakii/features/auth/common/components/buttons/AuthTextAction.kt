package com.tomildev.trakii.features.auth.common.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun AuthTextAction(
    modifier: Modifier = Modifier,
    text: String,
    actionText: String = "",
    onClick: () -> Unit,
    textAlign: TextAlign = TextAlign.Center,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }),
        horizontalArrangement = horizontalArrangement
    ) {
        Texts.Body(
            modifier = Modifier,
            text = text,
            textAlign = textAlign,
            color = MaterialTheme.colorScheme.primary,
            isSecondary = true
        )
        if (actionText.isNotEmpty()) {
            HorizontalSpacer(Dimens.SpacingTiny)
            Texts.Body(
                modifier = Modifier,
                text = actionText,
                textAlign = textAlign,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}