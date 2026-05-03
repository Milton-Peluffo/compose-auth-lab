package com.tomildev.trakii.core.common.presentation.components.snackbars

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun SnackbarBase(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: Painter,
    iconTint: Color,
    containerTint: Color,
    onClick: () -> Unit
) {
    val shape = MaterialTheme.shapes.large
    // Determines if the background is dark based on its brightness to adjust the border opacity.
    val isDarkTheme = MaterialTheme.colorScheme.surfaceVariant.luminance() < 0.5f
    val borderAlpha = if (isDarkTheme) Alpha.Secondary else Alpha.Full

    val blendedContainerColor = containerTint
        .copy(alpha = Alpha.Divider)
        .compositeOver(MaterialTheme.colorScheme.surfaceVariant)

    Snackbar(
        modifier = modifier
            .height(Dimens.SnackbarHeight)
            .padding(horizontal = 8.dp)
            .border(
                width = Dimens.BorderNormal,
                shape = shape,
                color = iconTint.copy(alpha = borderAlpha)
            ),
        shape = shape,
        containerColor = blendedContainerColor,
        action = null
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(Dimens.IconSizeMedium),
                painter = icon,
                contentDescription = null,
                tint = iconTint
            )

            HorizontalSpacer(Dimens.SpacingMedium)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Texts.Body(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!description.isNullOrBlank()) {
                    Texts.BodySmall(
                        text = description,
                        isSecondary = true,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_outlined),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Alpha.Hint)
                )
            }
        }
    }
}