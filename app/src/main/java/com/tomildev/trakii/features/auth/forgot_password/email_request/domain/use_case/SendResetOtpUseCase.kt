package com.tomildev.trakii.features.auth.forgot_password.email_request.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.AuthUserRepository
import com.tomildev.trakii.features.auth.forgot_password.email_request.domain.EmailRequestRepository
import javax.inject.Inject

class SendResetOtpUseCase @Inject constructor(
    private val emailRequestRepository: EmailRequestRepository,
    private val authUserRepository: AuthUserRepository
) {
    suspend operator fun invoke(email: String): Result<Unit, DataError.Network> {
        val cleanEmail = email.trim().lowercase()

        val profileResult = authUserRepository.getProfileByEmail(cleanEmail)

        if (profileResult is Result.Error) {
            return when (profileResult.error) {
                DataError.Network.NoInternet,
                DataError.Network.Timeout -> profileResult

                else -> emailRequestRepository.sendResetOtp(cleanEmail)
            }
        }

        val profile = (profileResult as Result.Success).data

        if (profile?.provider == "google") {
            return Result.Error(DataError.Network.GoogleAccountExists)
        }

        return emailRequestRepository.sendResetOtp(cleanEmail)
    }
}