package com.tomildev.room_login_compose.core.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tomildev.room_login_compose.R
import com.tomildev.room_login_compose.core.domain.model.ValidationError

@Composable
fun ValidationError.asString(): String {
    return when (this) {
        is ValidationError.EmptyField -> stringResource(R.string.error_empty_field)
        is ValidationError.TooShort -> stringResource(R.string.error_password_too_short)
        is ValidationError.InvalidFormat -> {
            stringResource(R.string.error_email_invalid)
        }
    }
}