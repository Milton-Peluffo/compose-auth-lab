package com.tomildev.room_login_compose.features.auth.otp.domain

import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.util.Result

interface OtpRepository {
    suspend fun resentOtp(email: String): Result<Unit, DataError.Network>
    suspend fun verifyOtp(email: String, otp: String): Result<Unit, DataError.Network>
}