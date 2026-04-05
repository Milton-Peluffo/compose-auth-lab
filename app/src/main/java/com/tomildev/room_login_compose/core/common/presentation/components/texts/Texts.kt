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
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier,
            textAlign = textAlign
        )
    }

    @Composable
    fun TitleLarge(
        text: String,
        color: Color,
        modifier: Modifier = Modifier,
        isSecondary: Boolean? = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            text = text,
            color = color,
            style = MaterialTheme.typography.titleLarge,
            alpha = if (isSecondary!!) 0.6f else 1f,
            modifier = modifier,
            textAlign = textAlign
        )
    }

    @Composable
    fun TitleMedium(
        text: String,
        modifier: Modifier = Modifier,
        isSecondary: Boolean = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            alpha = if (isSecondary) 0.6f else 1f,
            modifier = modifier,
            textAlign = textAlign
        )
    }

    @Composable
    fun Body(
        text: String,
        modifier: Modifier = Modifier,
        isSecondary: Boolean = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            alpha = if (isSecondary) 0.6f else 1f,
            modifier = modifier,
            textAlign = textAlign
        )
    }

    @Composable
    fun Label(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign = TextAlign.Center
    ) {
        AppText(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = color,
            modifier = modifier,
            textAlign = textAlign
        )
    }
}