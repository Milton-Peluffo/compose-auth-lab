package com.tomildev.trakii.features.auth.forgot_password.update_password.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.forgot_password.update_password.domain.UpdatePasswordRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import javax.inject.Inject

class UpdatePasswordRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : UpdatePasswordRepository {
    override suspend fun updatePassword(password: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.updateUser {
                this.password = password
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}
