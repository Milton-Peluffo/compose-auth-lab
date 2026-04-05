package com.tomildev.room_login_compose.features.auth.otp.presentation

import com.tomildev.room_login_compose.core.domain.model.error.DataError

interface OtpUiEvent {
    data class Error(val error: DataError) : OtpUiEvent
    data class Warning(val error: DataError) : OtpUiEvent
    data object CodeResent : OtpUiEvent
}