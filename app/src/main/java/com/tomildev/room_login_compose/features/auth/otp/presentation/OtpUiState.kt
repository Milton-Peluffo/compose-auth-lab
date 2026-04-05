package com.tomildev.room_login_compose.features.auth.otp.presentation

import com.tomildev.room_login_compose.core.domain.model.error.DataError

data class OtpUiState(
    // DATA
    val code: String = "",
    val email: String = "",
    val displayedEmail: String = "",

    // STATES / VALIDATORS
    val isVerifyEnable: Boolean = false,
    val isVerified: Boolean = false,
    val isLoading: Boolean = false,
    val canResend: Boolean = false,
    val timer: Int = 60,

    // ERRORS
    val networkError: DataError.Network? = null,
    val codeError: String? = null
)