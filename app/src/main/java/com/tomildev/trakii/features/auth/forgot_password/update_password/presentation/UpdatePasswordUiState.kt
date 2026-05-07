package com.tomildev.trakii.features.auth.forgot_password.update_password.presentation

import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class UpdatePasswordUiState(
    //USER DATA
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    // STATES / VALIDATORS
    val isLoading: Boolean = false,
    val isPasswordMatch: Boolean = false,
    val showCancelUpdateDialog: Boolean = false,

    //ERRORS
    val passwordError: UserValidationError? = null,
    val confirmPasswordError: UserValidationError? = null,
)
