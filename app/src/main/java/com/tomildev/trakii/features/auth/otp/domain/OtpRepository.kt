package com.tomildev.trakii.features.auth.otp.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface OtpRepository {
    suspend fun resentOtp(email: String): Result<Unit, DataError.Network>
    suspend fun verifyOtp(email: String, otp: String): Result<OtpVerificationResult, DataError.Network>
}

sealed interface OtpVerificationResult {
    data object UserExists : OtpVerificationResult
    data object NewUser : OtpVerificationResult
}