package com.tomildev.trakii.features.auth.otp.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import javax.inject.Inject

class ResendOtpUseCase @Inject constructor(
    private val repository: OtpRepository
) {
    suspend operator fun invoke(email: String): Result<Unit, DataError.Network> {
        return repository.resendOtp(email.trim().lowercase())
    }
}