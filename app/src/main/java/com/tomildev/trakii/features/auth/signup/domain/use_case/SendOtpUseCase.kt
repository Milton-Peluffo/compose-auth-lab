package com.tomildev.trakii.features.auth.signup.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.AuthUserRepository
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import javax.inject.Inject

/**
 * Business logic for sending an OTP code.
 * Orchestrates the check for existing profiles and decides whether to sign up or sign in.
 */
class SendOtpUseCase @Inject constructor(
    private val repository: SignUpRepository,
    private val authUserRepository: AuthUserRepository
) {
    suspend operator fun invoke(email: String): Result<Unit, DataError.Network> {
        val cleanEmail = email.trim().lowercase()
        val profileResult = authUserRepository.getProfileByEmail(cleanEmail)

        if (profileResult is Result.Error) return profileResult

        val profile = (profileResult as Result.Success).data
        if (profile?.provider == "google") {
            return Result.Error(DataError.Network.GoogleAccountExists)
        }

        val isComplete = profile?.isProfileComplete == true
        return if (!isComplete) {
            val signUpResult = repository.signUpWithOtp(cleanEmail)
            if (signUpResult is Result.Error) {
                repository.signInWithOtp(cleanEmail)
            } else {
                signUpResult
            }
        } else {
            repository.signInWithOtp(cleanEmail)
        }
    }
}