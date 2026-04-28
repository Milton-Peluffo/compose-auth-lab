package com.tomildev.trakii.core.domain.model.error

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
        data object ServiceUnavailable : Network()
        data object InvalidCredentials : Network()
        data object InvalidOtp : Network()
        data object Conflict : Network()
        data object GoogleAccountExists : Network()
        data object NoInternet : Network()
        data object Timeout : Network()
        data object Unknown : Network()
    }
}
