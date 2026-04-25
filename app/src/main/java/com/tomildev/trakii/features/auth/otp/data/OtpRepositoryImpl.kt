package com.tomildev.trakii.features.auth.otp.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.OtpVerificationResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject

class OtpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OtpRepository {

    override suspend fun verifyOtp(
        email: String,
        otp: String
    ): Result<OtpVerificationResult, DataError.Network> {
        return try {
            // Usamos MAGIC_LINK porque es el tipo que Supabase asigna a los OTP de email 
            // cuando se envían sin contraseña (Passwordless/Magic Link OTP).
            supabaseClient.auth.verifyEmailOtp(
                type = OtpType.Email.MAGIC_LINK,
                email = email.trim().lowercase(),
                token = otp
            )
            
            val user = supabaseClient.auth.retrieveUserForCurrentSession()
            val isNewUser = checkIfUserIsNew(user)
            
            if (isNewUser) {
                Result.Success(OtpVerificationResult.NewUser)
            } else {
                Result.Success(OtpVerificationResult.UserExists)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }

    private fun checkIfUserIsNew(user: UserInfo): Boolean {
        val metadata = user.userMetadata
        val displayName = metadata?.get("display_name")?.toString()?.replace("\"", "")
        return displayName.isNullOrBlank()
    }

    override suspend fun resentOtp(email: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.resendEmail(
                type = OtpType.Email.MAGIC_LINK,
                email = email.trim().lowercase()
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }
}