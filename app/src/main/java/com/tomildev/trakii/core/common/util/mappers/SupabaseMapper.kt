package com.tomildev.trakii.core.common.util.mappers

import com.tomildev.trakii.core.domain.model.error.DataError
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnauthorizedRestException

fun mapSupabaseError(
    e: Exception,
    specificMapping: (Exception) -> DataError.Network? = { null }
): DataError.Network {

    specificMapping(e)?.let { return it }

    return when (e) {
        is HttpRequestException -> {
            val message = e.message ?: ""
            when {
                message.contains("Unable to resolve host") -> DataError.Network.NoInternet
                message.contains("timeout") || message.contains("timed out") -> DataError.Network.Timeout
                else -> DataError.Network.NoInternet
            }
        }

        is UnauthorizedRestException -> DataError.Network.InvalidCredentials
        is RestException -> DataError.Network.ServiceUnavailable
        else -> DataError.Network.Unknown
    }
}