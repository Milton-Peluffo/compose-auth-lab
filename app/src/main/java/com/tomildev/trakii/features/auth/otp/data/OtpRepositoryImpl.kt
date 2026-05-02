package com.tomildev.trakii.features.auth.otp.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.OtpType
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.exceptions.RestException
import javax.inject.Inject
import io.github.jan.supabase.auth.OtpType as SupabaseOtpType

class OtpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OtpRepository {

    override suspend fun verifyOtp(
        email: String,
        otp: String,
        type: OtpType
    ): Result<Unit, DataError.Network> {
        val supabaseType = when (type) {
            OtpType.SIGNUP -> SupabaseOtpType.Email.SIGNUP
            OtpType.MAGIC_LINK -> SupabaseOtpType.Email.MAGIC_LINK
            OtpType.RECOVERY -> SupabaseOtpType.Email.RECOVERY
        }

        return try {
            supabaseClient.auth.verifyEmailOtp(
                type = supabaseType,
                email = email.trim().lowercase(),
                token = otp
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(
                mapSupabaseError(e) { exception ->
                    if (exception is RestException) {
                        val message = exception.message ?: ""
                        when {
                            message.contains("otp_expired", ignoreCase = true) ->
                                DataError.Network.InvalidOtp

                            else -> null
                        }
                    } else null
                }
            )
        }
    }

    override suspend fun resendOtp(email: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.signInWith(OTP) {
                this.email = email.trim().lowercase()
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}
