package com.tomildev.room_login_compose.core.common.presentation.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

object Texts {

    @Composable
    fun Headline(
        text: String,
        modifier: Modifier = Modifier,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            modifier = modifier,
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = textAlign
        )
    }

    @Composable
    fun TitleLarge(
        modifier: Modifier = Modifier,
        text: String,
        color: Color,
        isSecondary: Boolean? = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.titleLarge,
            alpha = if (isSecondary!!) 0.6f else 1f,
            textAlign = textAlign
        )
    }

    @Composable
    fun TitleMedium(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = Color.Unspecified,
        isSecondary: Boolean = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.titleMedium,
            alpha = if (isSecondary) 0.6f else 1f,
            textAlign = textAlign
        )
    }

    @Composable
    fun Body(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = Color.Unspecified,
        isSecondary: Boolean = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.bodyMedium,
            alpha = if (isSecondary) 0.6f else 1f,
            textAlign = textAlign
        )
    }

    @Composable
    fun Label(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = Color.Unspecified,
        textAlign: TextAlign = TextAlign.Center
    ) {
        AppText(
            modifier = modifier,
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = color,
            textAlign = textAlign
        )
    }
}