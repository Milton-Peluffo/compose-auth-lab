package com.tomildev.room_login_compose.core.domain.util

import com.tomildev.room_login_compose.core.domain.model.error.Error

/**
 * Represents the result of an operation that can either succeed with data of type [D]
 * or fail with an error of type [E].
 *
 * @param D The type of the data returned upon success.
 * @param E The type of the error returned upon failure, which must implement the [Error] interface.
 */

sealed class Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>()
    data class Error<out E : com.tomildev.room_login_compose.core.domain.model.error.Error>(val error: E) :
        Result<Nothing, E>()
}