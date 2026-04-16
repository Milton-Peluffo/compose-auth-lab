package com.tomildev.trakii.features.auth.otp.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

interface OtpUiEvent {
    data class Error(val error: DataError) : OtpUiEvent
    data class Warning(val error: DataError) : OtpUiEvent
    data object CodeResent : OtpUiEvent
}