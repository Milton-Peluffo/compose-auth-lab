package com.tomildev.trakii.features.auth.signin.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signin.domain.SignInRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : SignInRepository {

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(
                mapSupabaseError(e) { exception ->
                    if (exception is AuthRestException) {
                        val message = exception.message ?: ""
                        when {
                            message.contains("invalid_credentials", ignoreCase = true) ->
                                DataError.Network.InvalidCredentials

                            else -> DataError.Network.Unknown
                        }
                    } else null
                }
            )
        }
    }
}