package com.tomildev.trakii.features.auth.signin.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signin.domain.OAuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.RestException
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OAuthRepository {

    override suspend fun authWithGoogle(idToken: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.signInWith(IDToken) {
                this.idToken = idToken
                provider = Google
            }
            Result.Success(Unit)

        } catch (e: Exception) {

            Result.Error(
                mapSupabaseError(e) { exception ->
                    if (exception is RestException) {
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