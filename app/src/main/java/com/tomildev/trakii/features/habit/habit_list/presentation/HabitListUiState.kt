package com.tomildev.trakii.features.habit.habit_list.presentation

data class HabitItemState(
    val id: String,
    val title: String,
    val colorId: String,
    val icon: Int,
    val isCompleted: Boolean,
    val progress: Int
)

data class HabitListUiState(
    val name: String = "",
    val habits: List<HabitItemState> = emptyList()
)