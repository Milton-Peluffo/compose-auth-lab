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
import io.github.jan.supabase.exceptions.RestException
import javax.inject.Inject

/**
 * Handles One-Time Password (OTP) operations via Supabase, including 
 * verification for sign-up and login flows, and resending codes.
 */
class OtpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OtpRepository {

    /**
     * Verifies the provided OTP against Magic Link and Signup flows, then checks 
     * if the user's profile is complete to determine the next navigation step.
     */
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
            Result.Error(
                error = mapSupabaseError(e) { exception ->
                    if (exception is RestException) {
                        val message = exception.message ?: ""
                        if (message.contains("otp_expired", ignoreCase = true) ||
                            message.contains("invalid_grant", ignoreCase = true)
                        ) {
                            DataError.Network.InvalidOtp
                        } else null
                    } else null
                }
            )
        }
    }

    private fun checkIfUserIsComplete(user: UserInfo): Boolean {
        val isOAuthUser = user.identities?.any { it.provider != "email" } == true
        if (isOAuthUser) return true

        val metadata = user.userMetadata
        val displayName = metadata?.get("display_name")?.toString()?.replace("\"", "")
        val fullName = metadata?.get("full_name")?.toString()?.replace("\"", "")

        return !displayName.isNullOrBlank() || !fullName.isNullOrBlank()
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
