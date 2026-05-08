package com.tomildev.trakii.core.data.repository

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.repository.PasswordRepository
import com.tomildev.trakii.core.domain.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : PasswordRepository {
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
