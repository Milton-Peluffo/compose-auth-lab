package com.tomildev.trakii.features.auth.otp.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

/**
 * Contract for handling OTP-based authentication operations.
 *
 * Provides methods to request a new OTP and verify it,
 * returning the result needed to drive the authentication flow.
 */
interface OtpRepository {
    suspend fun resentOtp(email: String): Result<Unit, DataError.Network>
    suspend fun verifyOtp(email: String, otp: String): Result<OtpVerificationResult, DataError.Network>
}

/**
 * Result of a successful OTP verification.
 *
 * Used to determine the next step in the user flow
 * based on whether the user has already completed their profile.
 */
sealed interface OtpVerificationResult {
    data object UserExists : OtpVerificationResult
    data object NewUser : OtpVerificationResult
}