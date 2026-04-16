package com.tomildev.trakii.features.auth.signup.data

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.User
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnauthorizedRestException
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : SignUpRepository {

    override suspend fun signUp(
        user: User,
        password: String
    ): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.signUpWith(Email) {
                email = user.email
                this.password = password
                data = buildJsonObject {
                    put("display_name", user.name)
                }
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