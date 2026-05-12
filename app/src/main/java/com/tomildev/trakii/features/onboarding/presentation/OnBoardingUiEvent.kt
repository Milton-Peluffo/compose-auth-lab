package com.tomildev.trakii.features.onboarding.presentation

sealed interface OnBoardingUiEvent {
    data object NavigateToHabitList : OnBoardingUiEvent
}