package com.tomildev.trakii.features.auth.signup.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(email: String): Result<Unit, DataError.Network> {
        val cleanEmail = email.trim().lowercase()
        return repository.signUpWithOtp(cleanEmail)
    }
}