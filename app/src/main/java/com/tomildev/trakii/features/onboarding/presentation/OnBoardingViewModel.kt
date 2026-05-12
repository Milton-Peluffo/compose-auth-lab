package com.tomildev.trakii.features.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.onboarding.domain.use_case.CompleteOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnBoardingUiState())
    val uiState: StateFlow<OnBoardingUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<OnBoardingUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onFinishOnboarding() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = completeOnboardingUseCase()
            when (result) {
                is Result.Success -> {
                    _uiEvents.send(OnBoardingUiEvent.NavigateToHabitList)
                }

                is Result.Error -> Unit
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}