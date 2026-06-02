package com.tomildev.trakii.features.habit.habit_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.cards.HabitiiCard
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens
import com.tomildev.trakii.ui.theme.ExtendedTheme
import com.tomildev.trakii.ui.theme.HabitColor

@Composable
fun HabitListItem(
    modifier: Modifier = Modifier,
    colorId: String,
    icon: Int,
    title: String,
    repetitionDays: List<Int>,
    completionPercentage: Int,
    isCompleted: Boolean
) {

    val habitColor = HabitColor.fromId(id = colorId).getColor(ExtendedTheme.colors)

    HabitiiCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(Dimens.SpacingMedium)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .fillMaxHeight()
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(color = habitColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            HorizontalSpacer(Dimens.SpacingMedium)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Texts.TitleMedium(
                    text = title,
                    textAlign = TextAlign.Start
                )

                VerticalSpacer(Dimens.SpacingTiny)

                HabitDaysResume(
                    selectedDays = repetitionDays,
                    accentColor = habitColor
                )

                VerticalSpacer(Dimens.SpacingTiny)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HabitProgressBar(
                        percentage = completionPercentage,
                        color = habitColor,
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(Dimens.SpacingTiny)
                    Texts.Body(
                        text = "$completionPercentage%",
                        textAlign = TextAlign.End,
                        color = habitColor
                    )
                }
            }

            HorizontalSpacer(Dimens.SpacingMedium)

            val checkContainerSize = 40.dp
            Box(
                modifier = Modifier
                    .size(checkContainerSize)
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(
                        color = if (isCompleted) {
                            ExtendedTheme.colors.success
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = Alpha.Overlay)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_check_outlined),
                    contentDescription = null,
                    tint = if (isCompleted) Color.White else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.5f
                    )
                )
            }
        }
    }
}

@Composable
fun HabitDaysResume(
    selectedDays: List<Int>,
    accentColor: Color
) {
    val dayNames = listOf("M", "T", "W", "T", "F", "S", "S")

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        dayNames.forEachIndexed { index, name ->
            val dayNumber = index + 1
            val isSelected = selectedDays.contains(dayNumber)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = name,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                    color = if (isSelected) accentColor else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.2f
                    )
                )
            }
        }
    }
}

@Composable
fun HabitProgressBar(
    percentage: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val progressFraction = (percentage.coerceIn(0, 100) / 100f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = Alpha.Overlay))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progressFraction)
                .clip(CircleShape)
                .background(color)
        )
    }
}