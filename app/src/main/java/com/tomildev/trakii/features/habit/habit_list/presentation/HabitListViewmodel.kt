package com.tomildev.trakii.features.habit.habit_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.R
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.copy

@HiltViewModel
class HabitListViewmodel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
        loadMockHabits()
    }

    private fun loadMockHabits() {
        val mocks = listOf(
            HabitItemState("1", "Meditar", "sage", R.drawable.ic_star_outlined, true, 100),
            HabitItemState("2", "Beber Agua", "muted_blue", R.drawable.ic_star_outlined, false, 40),
            HabitItemState("3", "Leer", "rose", R.drawable.ic_star_outlined, false, 0),
            HabitItemState("4", "Ejercicio", "sand", R.drawable.ic_star_outlined, true, 80)
        )
        _uiState.update { it.copy(habits = mocks) }
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            sessionRepository.observeSession().collect { sessionState ->
                if (sessionState is SessionState.Authenticated) {
                    _uiState.update { habitListUiState ->
                        habitListUiState.copy(
                            name = sessionState.user.displayName
                        )
                    }
                }
            }
        }
    }
}