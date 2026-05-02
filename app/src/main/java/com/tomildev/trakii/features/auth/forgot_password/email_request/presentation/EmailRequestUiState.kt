package com.tomildev.trakii.features.auth.forgot_password.email_request.presentation

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class EmailRequestUiState(
    //USER DATA
    val email: String = "",

    // STATES / VALIDATORS
    val isLoading: Boolean = false,

    //ERRORS
    val networkError: DataError? = null,
    val emailError: UserValidationError? = null,
)