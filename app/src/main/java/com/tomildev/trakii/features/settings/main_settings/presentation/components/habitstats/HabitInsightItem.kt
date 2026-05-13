package com.tomildev.trakii.features.settings.main_settings.presentation.components.habitstats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.core.common.presentation.components.dividers.Dividers
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun HabitInsightItem(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    title: String,
    value: String,
    showDivider: Boolean = true
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = Alpha.Secondary
                    )
                )
            }

            HorizontalSpacer(Dimens.SpacingSmall)

            Texts.Body(
                modifier = Modifier.weight(1f),
                text = title
            )

            Texts.LabelMedium(
                text = value,
                isSecondary = true
            )
        }

        if (showDivider) {
            Dividers.HorizontalDivider()
        }
    }
}