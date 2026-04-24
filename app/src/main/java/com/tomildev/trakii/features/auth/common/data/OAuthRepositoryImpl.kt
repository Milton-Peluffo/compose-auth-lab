package com.tomildev.trakii.features.auth.common.data

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.common.domain.OAuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnauthorizedRestException
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : OAuthRepository {

    override suspend fun authWithGoogle(idToken: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.signUpWith(IDToken) {
                this.idToken = idToken
                provider = Google
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(error = mapSupabaseError(e))
        }
    }

    private fun mapSupabaseError(e: Exception): DataError.Network {
        return when (e) {
            is AuthRestException -> {
                val message = e.message ?: ""

                when {
                    message.contains("user_already_exists") -> DataError.Network.Conflict
                    else -> DataError.Network.Unknown
                }
            }

            is HttpRequestException -> {
                val message = e.message ?: ""

                when {
                    message.contains("Unable to resolve host") -> DataError.Network.NoInternet
                    message.contains("timeout") || message.contains("timed out") -> DataError.Network.Timeout
                    else -> DataError.Network.NoInternet
                }
            }

            is UnauthorizedRestException -> {
                DataError.Network.InvalidOtp
            }

            is RestException -> {
                DataError.Network.ServiceUnavailable
            }

            else -> DataError.Network.Unknown
        }
    }
}