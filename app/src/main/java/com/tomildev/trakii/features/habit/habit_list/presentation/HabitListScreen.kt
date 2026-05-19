@file:OptIn(ExperimentalMaterial3Api::class)

package com.tomildev.trakii.features.habit.habit_list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.features.habit.habit_list.presentation.components.HabitListItem
import com.tomildev.trakii.ui.theme.Dimens
import com.tomildev.trakii.ui.theme.ExtendedTheme

@Composable
fun HabitListScreen(
    habitListViewmodel: HabitListViewmodel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
) {

    val uiState by habitListViewmodel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_gear_outlined),
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                windowInsets = TopAppBarDefaults.windowInsets
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalSpacer(Dimens.SpacingSmall)
                    HabitListItem(
                        habitColor = ExtendedTheme.colors.warning,
                        icon = R.drawable.ic_star_outlined,
                        title = "Test Habit",
                        repetitionDays = listOf(1, 3, 5, 2),
                        completionPercentage = 77,
                        isCompleted = true
                    )
                    VerticalSpacer(Dimens.SpacingSmall)
                    HabitListItem(
                        habitColor = ExtendedTheme.colors.warning,
                        icon = R.drawable.ic_star_outlined,
                        title = "Test Habit",
                        repetitionDays = listOf(1, 3, 5, 2),
                        completionPercentage = 77,
                        isCompleted = true
                    )
                    VerticalSpacer(Dimens.SpacingSmall)
                    HabitListItem(
                        habitColor = ExtendedTheme.colors.warning,
                        icon = R.drawable.ic_star_outlined,
                        title = "Test Habit",
                        repetitionDays = listOf(1, 3, 5, 2),
                        completionPercentage = 77,
                        isCompleted = true
                    )
                    VerticalSpacer(Dimens.SpacingSmall)
                    HabitListItem(
                        habitColor = ExtendedTheme.colors.warning,
                        icon = R.drawable.ic_star_outlined,
                        title = "Test Habit",
                        repetitionDays = listOf(1, 3, 5, 2),
                        completionPercentage = 77,
                        isCompleted = true
                    )
                    VerticalSpacer(Dimens.SpacingSmall)
                    HabitListItem(
                        habitColor = ExtendedTheme.colors.warning,
                        icon = R.drawable.ic_star_outlined,
                        title = "Test Habit",
                        repetitionDays = listOf(1, 3, 5, 2),
                        completionPercentage = 77,
                        isCompleted = true
                    )
                }
            }
        }
    }
}