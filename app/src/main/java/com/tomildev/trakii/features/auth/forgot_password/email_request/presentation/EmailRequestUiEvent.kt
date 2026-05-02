package com.tomildev.trakii.features.auth.forgot_password.email_request.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

sealed interface EmailRequestUiEvent {
    data class NavigateToOtp(val email: String) : EmailRequestUiEvent
    data class Error(val error: DataError) : EmailRequestUiEvent
    data class Warning(val error: DataError) : EmailRequestUiEvent
}