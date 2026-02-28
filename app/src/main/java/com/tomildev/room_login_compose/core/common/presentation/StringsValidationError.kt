package com.tomildev.room_login_compose.core.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tomildev.room_login_compose.R
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError

@Composable
fun UserValidationError.asString(): String {
    return when (this) {
        is UserValidationError.EmptyField -> stringResource(R.string.error_empty_field)
        is UserValidationError.TooShortName -> stringResource(R.string.error_user_name_too_short)
        is UserValidationError.InvalidName -> stringResource(R.string.error_user_name_invalid)
        is UserValidationError.InvalidEmail -> stringResource(R.string.error_email_invalid)
        is UserValidationError.InvalidPassword -> stringResource(R.string.error_email_invalid)
        is UserValidationError.TooShortPassword -> stringResource(R.string.error_password_too_short)
        is UserValidationError.PasswordDoNotMatch -> stringResource(R.string.error_confirm_password_match)
    }
}