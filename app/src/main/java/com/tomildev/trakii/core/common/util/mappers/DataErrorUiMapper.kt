package com.tomildev.trakii.core.common.util.mappers

import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.util.ui.UiText
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class DataErrorUiText(
    val title: UiText,
    val description: UiText? = null
)

fun UserValidationError.toUiText(): UiText {
    val resId = when (this) {
        UserValidationError.EmptyField -> R.string.common_error_empty_field
        UserValidationError.TooShortName -> R.string.common_error_user_name_too_short
        UserValidationError.TooLongName -> R.string.common_error_user_name_too_long
        UserValidationError.InvalidName -> R.string.common_error_user_name_invalid
    }
    return UiText.StringResource(resId)
}

fun DataError.toUiText(): DataErrorUiText {
    return when (this) {
        is DataError.Network -> {
            val titleResId = when (this) {
                DataError.Network.ServiceUnavailable -> R.string.common_error_network_service_unavailable
                DataError.Network.InvalidCredentials -> R.string.auth_signin_network_error_invalid_credentials
                DataError.Network.NoInternet -> R.string.common_error_network_no_internet
                DataError.Network.Timeout -> R.string.common_error_network_timeout
                DataError.Network.Unknown -> R.string.common_error_network_unknown
            }
            
            val descriptionResId = when (this) {
                DataError.Network.ServiceUnavailable -> R.string.common_error_subtitle_try_later
                DataError.Network.NoInternet -> R.string.common_error_subtitle_check_connection
                DataError.Network.Timeout -> R.string.common_error_subtitle_try_later
                else -> null
            }
            
            DataErrorUiText(
                title = UiText.StringResource(titleResId),
                description = descriptionResId?.let { UiText.StringResource(it) }
            )
        }
    }
}
