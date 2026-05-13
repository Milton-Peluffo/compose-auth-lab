package com.tomildev.trakii.features.settings.main_settings.presentation.components.habitstats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.cards.HabitiiCard
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha

@Composable
fun HabitInsightsSection(
    modifier: Modifier = Modifier,
    currentHabits: String,
    timeInvested: String,
    bestStreak: String
) {

    HabitiiCard(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {

            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_trending_up_utlined),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = Alpha.Secondary
                        )
                    )

                    HorizontalSpacer(8.dp)

                    Texts.Body(
                        text = "Tu impacto"
                    )
                }

                VerticalSpacer(4.dp)

                Texts.LabelMedium(
                    text = "Pequeños habitos, grandes cambios.",
                    isSecondary = true
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)

            ) {
                HabitInsightItem(
                    leadingIcon = R.drawable.ic_plant_filled,
                    title = "Rutinas activas",
                    value = currentHabits
                )

                HabitInsightItem(
                    leadingIcon = R.drawable.ic_clock_filled,
                    title = "Tiempo invertido",
                    value = timeInvested
                )

                HabitInsightItem(
                    leadingIcon = R.drawable.ic_crown_filled,
                    title = "Mejor racha",
                    value = bestStreak,
                    showDivider = false
                )
            }
        }
    }
}