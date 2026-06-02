@file:OptIn(ExperimentalMaterial3Api::class)

package com.tomildev.trakii.features.habit.habit_list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.features.habit.habit_list.presentation.components.HabitListItem
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun HabitListScreen(
    habitListViewmodel: HabitListViewmodel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
) {
    // Obtenemos el estado del ViewModel
    val uiState by habitListViewmodel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // Aquí podrías poner el nombre del usuario que está en el uiState
                    Texts.TitleLarge(text = "Mis Hábitos")
                },
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
                )
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            // Espaciado entre elementos de la lista
            contentPadding = PaddingValues(vertical = Dimens.SpacingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall)
        ) {
            // Iteramos sobre la lista de hábitos del uiState
            items(uiState.habits) { habit ->
                HabitListItem(
                    colorId = habit.colorId,
                    icon = habit.icon,
                    title = habit.title,
                    // Por ahora pasamos una lista fija, luego vendrá de tu lógica de negocio
                    repetitionDays = listOf(1, 2, 3, 4, 5),
                    completionPercentage = habit.progress,
                    isCompleted = habit.isCompleted
                )
            }

            // UI en caso de que no haya hábitos
            if (uiState.habits.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Texts.Body(
                            text = "No tienes hábitos registrados.\n¡Comienza uno nuevo!",
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}