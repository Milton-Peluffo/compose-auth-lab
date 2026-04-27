package com.tomildev.trakii.features.auth.signup.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : SignUpRepository {

    override suspend fun sendOtp(email: String): Result<Unit, DataError.Network> {
        val cleanEmail = email.trim().lowercase()
        return try {
            supabaseClient.auth.signInWith(OTP) {
                this.email = cleanEmail
                createUser = true
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }

    override suspend fun completeRegistration(
        name: String,
        password: String
    ): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.updateUser {
                this.password = password
                data = buildJsonObject {
                    put("display_name", name)
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}