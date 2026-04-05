package com.tomildev.room_login_compose.core.common.presentation.mapper

import com.tomildev.room_login_compose.R
import com.tomildev.room_login_compose.core.common.presentation.util.UiText
import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError

fun UserValidationError.toUiText(): UiText {
    val resId = when (this) {
        UserValidationError.EmptyField -> R.string.error_empty_field
        UserValidationError.TooShortName -> R.string.error_user_name_too_short
        UserValidationError.InvalidName -> R.string.error_user_name_invalid
        UserValidationError.InvalidEmail -> R.string.error_email_invalid
        UserValidationError.TooShortPassword -> R.string.error_password_too_short
        UserValidationError.InvalidPassword -> R.string.error_password_format
        UserValidationError.PasswordDoNotMatch -> R.string.error_confirm_password_match
    }
    return UiText.StringResource(resId)
}

fun DataError.toUiText(): UiText {
    return when (this) {
        is DataError.Network -> {
            val redId = when (this) {
                DataError.Network.ServiceUnavailable -> R.string.error_network_service_unavailable_error
                DataError.Network.InvalidCredentials -> R.string.login_network_invalid_credentials_error
                DataError.Network.InvalidOtp -> R.string.otp_network_invalid_code_error
                DataError.Network.Conflict -> R.string.error_network_email_already_exists_error
                DataError.Network.NoInternet -> R.string.error_network_no_internet_error
                DataError.Network.Timeout -> R.string.error_network_timeout_error
                DataError.Network.Unknown -> R.string.error_network_unknown_error
            }
            UiText.StringResource(redId)
        }
    }
}