package com.tomildev.trakii.features.auth.signin.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

sealed interface SignInUiEvent {
    data class NavigateToHabitList(val email: String) : SignInUiEvent
    data object NavigateToSignUp : SignInUiEvent
    data class Error(val error: DataError) : SignInUiEvent
    data class Warning(val error: DataError) : SignInUiEvent
}