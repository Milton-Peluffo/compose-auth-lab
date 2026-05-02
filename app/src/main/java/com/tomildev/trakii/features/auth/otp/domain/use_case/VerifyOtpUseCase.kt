package com.tomildev.trakii.features.auth.otp.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.AuthUserRepository
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.OtpType
import com.tomildev.trakii.features.auth.otp.domain.OtpVerificationResult
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val otpRepository: OtpRepository,
    private val authUserRepository: AuthUserRepository
) {
    suspend operator fun invoke(
        email: String,
        otp: String
    ): Result<OtpVerificationResult, DataError.Network> {
        val cleanEmail = email.trim().lowercase()
        
        var verifyResult = otpRepository.verifyOtp(cleanEmail, otp, OtpType.RECOVERY)

        if (verifyResult is Result.Error) {
            verifyResult = otpRepository.verifyOtp(cleanEmail, otp, OtpType.MAGIC_LINK)
        }

        if (verifyResult is Result.Error) {
            verifyResult = otpRepository.verifyOtp(cleanEmail, otp, OtpType.SIGNUP)
        }

        if (verifyResult is Result.Error) return Result.Error(verifyResult.error)

        return when (val profileResult = authUserRepository.getProfileByEmail(email = cleanEmail)) {
            is Result.Error -> Result.Error(profileResult.error)
            is Result.Success -> {
                val profile = profileResult.data
                if (profile?.isProfileComplete == true) {
                    Result.Success(OtpVerificationResult.UserExists)
                } else {
                    Result.Success(OtpVerificationResult.NewUser)
                }
            }
        }
    }
}