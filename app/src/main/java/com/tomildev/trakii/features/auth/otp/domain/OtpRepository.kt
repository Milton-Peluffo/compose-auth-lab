package com.tomildev.trakii.features.auth.otp.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface OtpRepository {
    suspend fun verifyOtp(email: String, otp: String, type: OtpType): Result<Unit, DataError.Network>
    suspend fun resendOtp(email: String): Result<Unit, DataError.Network>
}

enum class OtpType {
    SIGNUP, MAGIC_LINK
}

sealed interface OtpVerificationResult {
    data object UserExists : OtpVerificationResult
    data object NewUser : OtpVerificationResult
}