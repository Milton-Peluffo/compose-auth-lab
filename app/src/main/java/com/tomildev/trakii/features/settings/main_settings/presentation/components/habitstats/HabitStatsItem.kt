package com.tomildev.trakii.features.settings.main_settings.presentation.components.habitstats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun HabitStatItem(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    description: String,
    icon: Painter,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = Alpha.Secondary),
            modifier = Modifier.size(Dimens.IconSizeMedium)
        )
        VerticalSpacer(Dimens.SpacingTiny)
        Texts.TitleMedium(
            text = value
        )
        VerticalSpacer(Dimens.SpacingTiny)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Texts.LabelMedium(
                text = title,
            )
            VerticalSpacer(Dimens.SpacingTiny)
            Texts.LabelMedium(
                text = description,
                isSecondary = true
            )
        }
    }
}