package com.tomildev.trakii.features.auth.signup.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

sealed interface SignUpUiEvent {
    data class Error(val error: DataError) : SignUpUiEvent
    data class Warning(val error: DataError) : SignUpUiEvent
    data class NavigateToOtp(val email: String) : SignUpUiEvent
}