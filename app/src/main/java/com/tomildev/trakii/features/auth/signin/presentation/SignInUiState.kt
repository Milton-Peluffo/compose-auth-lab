package com.tomildev.trakii.features.auth.signin.presentation

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class SignInUiState(
    //USER DATA
    val email: String = "",
    val password: String = "",

    // STATES / VALIDATORS
    val isLoading: Boolean = false,
    val isGoogleLoading: Boolean = false,
    val showReauthenticationRequiredDialog: Boolean = false,

    //ERRORS
    val networkError: DataError? = null,
    val emailError: UserValidationError? = null,
    val passwordError: UserValidationError? = null,
)
