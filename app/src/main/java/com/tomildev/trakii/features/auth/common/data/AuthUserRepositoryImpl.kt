package com.tomildev.trakii.features.auth.common.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.data.remote.dto.ProfileDto
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.AuthUserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class AuthUserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthUserRepository {
    override suspend fun getProfileByEmail(email: String): Result<ProfileDto?, DataError.Network> {
        return try {
            val profile = supabaseClient.from("profiles")
                .select { filter { eq("email", email) } }
                .decodeSingleOrNull<ProfileDto>()
            Result.Success(profile)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}