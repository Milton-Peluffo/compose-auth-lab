package com.tomildev.trakii.features.auth.signup.presentation.signup

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class SignUpUiState(
    //USER DATA
    val email: String = "",

    // STATES / VALIDATORS
    val isLoading: Boolean = false,
    val isGoogleLoading: Boolean = false,

    //ERRORS
    val networkError: DataError? = null,
    val errorMessage: String? = null,
    val emailError: UserValidationError? = null,
)