package com.tomildev.trakii.features.auth.otp.data

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.exceptions.HttpRequestException
import javax.inject.Inject

class OtpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OtpRepository {

    override suspend fun verifyOtp(
        email: String,
        otp: String
    ): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.verifyEmailOtp(
                type = OtpType.Email.SIGNUP,
                email = email.trim().lowercase(),
                token = otp
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }

    override suspend fun resentOtp(email: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.resendEmail(
                type = OtpType.Email.SIGNUP,
                email = email.trim().lowercase()
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }

    private fun mapSupabaseError(e: Exception): DataError.Network {
        return when (e) {

            is HttpRequestException -> {
                val message = e.message ?: ""

                when {
                    message.contains("Unable to resolve host") -> DataError.Network.NoInternet
                    message.contains("timeout") || message.contains("timed out") -> DataError.Network.Timeout
                    message.contains("otp_expired") -> DataError.Network.InvalidOtp
                    else -> DataError.Network.NoInternet
                }
            }

            else -> DataError.Network.Unknown
        }
    }
}