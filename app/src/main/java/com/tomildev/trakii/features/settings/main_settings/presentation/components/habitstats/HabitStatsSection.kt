package com.tomildev.trakii.features.settings.main_settings.presentation.components.habitstats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.cards.HabitiiCard
import com.tomildev.trakii.core.common.presentation.components.dividers.Dividers
import com.tomildev.trakii.core.common.presentation.components.texts.Texts

@Composable
fun HabitStatsSection(
    completed: String,
    currentStreak: String,
    successRate: String
) {

    HabitiiCard {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
        ) {

            Texts.Body(
                text = "Resumen de habitos"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                HabitStatItem(
                    modifier = Modifier.weight(1f),
                    value = completed,
                    title = "Completados",
                    description = "Sigue asi",
                    icon = painterResource(R.drawable.ic_circle_check_outlined)
                )
                Dividers.VerticalDivider()
                HabitStatItem(
                    modifier = Modifier.weight(1f),
                    value = currentStreak,
                    title = "Racha actual",
                    description = "Dias seguidos",
                    icon = painterResource(R.drawable.ic_flame_outlined)
                )
                Dividers.VerticalDivider()
                HabitStatItem(
                    modifier = Modifier.weight(1f),
                    value = successRate,
                    title = "Tasa de exito",
                    description = "Promedio",
                    icon = painterResource(R.drawable.ic_star_outlined)
                )
            }
        }
    }
}