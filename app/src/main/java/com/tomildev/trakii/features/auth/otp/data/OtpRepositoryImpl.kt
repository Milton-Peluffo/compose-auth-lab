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
            try {
                supabaseClient.auth.verifyEmailOtp(
                    type = OtpType.Email.MAGIC_LINK,
                    email = cleanEmail,
                    token = otp
                )
            } catch (e: Exception) {
                supabaseClient.auth.verifyEmailOtp(
                    type = OtpType.Email.SIGNUP,
                    email = cleanEmail,
                    token = otp
                )
            }

            val user = supabaseClient.auth.retrieveUserForCurrentSession()
            val isComplete = checkIfUserIsComplete(user)

            if (isComplete) {
                Result.Success(OtpVerificationResult.UserExists)
            } else {
                Result.Success(OtpVerificationResult.NewUser)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }

    private fun checkIfUserIsComplete(user: UserInfo): Boolean {
        val metadata = user.userMetadata
        val displayName = metadata?.get("display_name")?.toString()?.replace("\"", "")
        return !displayName.isNullOrBlank()
    }

    override suspend fun resentOtp(email: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.signInWith(OTP) {
                this.email = email.trim().lowercase()
                createUser = true
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(error = mapSupabaseError(e))
        }
    }
}