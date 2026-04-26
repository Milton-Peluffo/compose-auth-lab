package com.tomildev.trakii.core.common.presentation.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.tomildev.trakii.ui.theme.Alpha

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
        isSecondary: Boolean = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        AppText(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.titleLarge,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
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
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
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
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
            textAlign = textAlign
        )
    }

    @Composable
    fun BodySmall(
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
            style = MaterialTheme.typography.bodySmall,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
            textAlign = textAlign
        )
    }

    @Composable
    fun LabelLarge(
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

    @Composable
    fun LabelMedium(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = Color.Unspecified,
        textAlign: TextAlign = TextAlign.Center
    ) {
        AppText(
            modifier = modifier,
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = color,
            textAlign = textAlign
        )
    }
}
