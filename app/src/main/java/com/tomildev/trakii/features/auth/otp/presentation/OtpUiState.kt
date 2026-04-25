package com.tomildev.trakii.features.auth.otp.presentation

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.features.auth.otp.domain.OtpVerificationResult

data class OtpUiState(
    // DATA
    val code: String = "",
    val email: String = "",
    val displayedEmail: String = "",
    val verificationResult: OtpVerificationResult? = null,

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