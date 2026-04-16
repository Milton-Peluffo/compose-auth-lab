package com.tomildev.trakii.core.common.presentation.mapper

import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.util.UiText
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError

fun UserValidationError.toUiText(): UiText {
    val resId = when (this) {
        UserValidationError.EmptyField -> R.string.common_error_empty_field
        UserValidationError.TooShortName -> R.string.common_error_user_name_too_short
        UserValidationError.InvalidName -> R.string.common_error_user_name_invalid
        UserValidationError.InvalidEmail -> R.string.common_error_email_invalid
        UserValidationError.TooShortPassword -> R.string.common_error_password_too_short
        UserValidationError.InvalidPassword -> R.string.common_error_password_format
        UserValidationError.PasswordDoNotMatch -> R.string.common_error_password_match
    }
    return UiText.StringResource(resId)
}

fun DataError.toUiText(): UiText {
    return when (this) {
        is DataError.Network -> {
            val redId = when (this) {
                DataError.Network.ServiceUnavailable -> R.string.common_error_network_service_unavailable
                DataError.Network.InvalidCredentials -> R.string.auth_signin_network_error_invalid_credentials
                DataError.Network.InvalidOtp -> R.string.auth_shared_otp_error_network_invalid_code
                DataError.Network.Conflict -> R.string.common_error_network_email_exists
                DataError.Network.NoInternet -> R.string.common_error_network_no_internet
                DataError.Network.Timeout -> R.string.common_error_network_timeout
                DataError.Network.Unknown -> R.string.common_error_network_unknown
            }
            UiText.StringResource(redId)
        }
    }
}