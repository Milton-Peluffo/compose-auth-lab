package com.tomildev.trakii.features.auth.forgot_password.email_request.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface EmailRequestRepository {
    suspend fun sendResetOtp(email: String): Result<Unit, DataError.Network>
}