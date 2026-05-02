package com.tomildev.trakii.features.auth.forgot_password.email_request.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.forgot_password.email_request.domain.EmailRequestRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import javax.inject.Inject

class EmailRequestRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : EmailRequestRepository {
    override suspend fun sendResetOtp(email: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.resetPasswordForEmail(email)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}