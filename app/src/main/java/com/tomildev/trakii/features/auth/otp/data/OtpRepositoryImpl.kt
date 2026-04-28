package com.tomildev.trakii.features.auth.otp.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.data.remote.dto.ProfileDto
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.OtpVerificationResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

/**
 * Handles One-Time Password (OTP) operations via Supabase, including
 * verification for sign-up, login flows and resending codes.
 */
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

            val user = supabaseClient.auth.currentUserOrNull()
                ?: return Result.Error(DataError.Network.ServiceUnavailable)

            val profile = supabaseClient.from("profiles")
                .select {
                    filter {
                        eq("id", user.id)
                    }
                }
                .decodeSingleOrNull<ProfileDto>()

            if (profile?.isProfileComplete == true) {
                Result.Success(OtpVerificationResult.UserExists)
            } else {
                Result.Success(OtpVerificationResult.NewUser)
            }

        } catch (e: Exception) {

            Result.Error(
                mapSupabaseError(e) { exception ->
                    if (exception is RestException) {
                        val message = exception.message ?: ""
                        when {
                            message.contains("otp_expired", ignoreCase = true) ->
                                DataError.Network.InvalidOtp

                            else -> DataError.Network.Unknown
                        }
                    } else null
                }
            )
        }
    }

    override suspend fun resentOtp(email: String): Result<Unit, DataError.Network> {
        val cleanEmail = email.trim().lowercase()

        return try {
            supabaseClient.auth.signInWith(OTP) {
                this.email = cleanEmail
            }

            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}