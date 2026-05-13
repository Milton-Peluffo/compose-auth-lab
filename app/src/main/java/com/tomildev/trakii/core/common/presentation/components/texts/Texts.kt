package com.tomildev.trakii.core.common.presentation.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
        color: Color = Color.Unspecified,
        textAlign: TextAlign = TextAlign.Start,
        isSecondary: Boolean = false,
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
        textAlign: TextAlign = TextAlign.Start,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        AppText(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.titleMedium,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
        )
    }

    @Composable
    fun TitleSmall(
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
            style = MaterialTheme.typography.titleSmall,
            textAlign = textAlign,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
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
            textAlign = textAlign,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
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
            textAlign = textAlign,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
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
        isSecondary: Boolean = false,
        textAlign: TextAlign = TextAlign.Center,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        AppText(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.labelMedium,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
            alpha = if (isSecondary) Alpha.Secondary else Alpha.Full,
        )
    }
}
