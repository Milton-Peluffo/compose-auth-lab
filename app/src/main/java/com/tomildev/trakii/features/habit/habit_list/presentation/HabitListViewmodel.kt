package com.tomildev.trakii.features.habit.habit_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitListViewmodel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
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

data class HabitListUiState(
    val name: String = "",
)
