package com.tomildev.room_login_compose.core.domain.model.error

/**
 * Represents all application-specific errors in the domain layer.
 *
 * This hierarchy groups different types of failures
 * into a single, type-safe structure, allowing consistent and exhaustive error
 * handling across the app.
 */
sealed interface Error
sealed class DataError : Error {
    sealed class Network : DataError() {
        object ServiceUnavailable : Network()
        object InvalidCredentials : Network()
        object InvalidOtp : Network()
        object Conflict : Network()
        object NoInternet : Network()
        object Timeout : Network()
        object Unknown : Network()
    }
}