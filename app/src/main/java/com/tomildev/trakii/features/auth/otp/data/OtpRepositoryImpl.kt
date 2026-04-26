package com.tomildev.trakii.features.auth.otp.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.OtpVerificationResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject

class OtpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OtpRepository {

    override suspend fun verifyOtp(
        email: String,
        otp: String
    ): Result<OtpVerificationResult, DataError.Network> {
        val cleanEmail = email.trim().lowercase()

        return try {
            supabaseClient.auth.verifyEmailOtp(
                type = OtpType.Email.MAGIC_LINK,
                email = cleanEmail,
                token = otp
            )
            handleSuccessfulVerification()
        } catch (e1: Exception) {
            e1.printStackTrace()
            try {
                supabaseClient.auth.verifyEmailOtp(
                    type = OtpType.Email.SIGNUP,
                    email = cleanEmail,
                    token = otp
                )
                handleSuccessfulVerification()
            } catch (e2: Exception) {
                e2.printStackTrace()
                Result.Error(error = mapSupabaseError(e2))
            }
        }
    }

    private suspend fun handleSuccessfulVerification(): Result<OtpVerificationResult, DataError.Network> {
        val user = supabaseClient.auth.retrieveUserForCurrentSession()
        val isNewUser = checkIfUserIsNew(user)

        return if (isNewUser) {
            Result.Success(OtpVerificationResult.NewUser)
        } else {
            Result.Success(OtpVerificationResult.UserExists)
        }
    }

    private fun checkIfUserIsNew(user: UserInfo): Boolean {
        val metadata = user.userMetadata
        val displayName = metadata?.get("display_name")?.toString()?.replace("\"", "")
        return displayName.isNullOrBlank()
    }

    override suspend fun resentOtp(email: String): Result<Unit, DataError.Network> {
        val cleanEmail = email.trim().lowercase()
        return try {
            supabaseClient.auth.signInWith(OTP) {
                this.email = cleanEmail
                createUser = true
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }
}