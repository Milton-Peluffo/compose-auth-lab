package com.tomildev.trakii.core.common.presentation.password_update

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.navigation.NavRoute

data class UpdatePasswordUiState(
    //USER DATA
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    // STATES / VALIDATORS
    val origin: NavRoute.PasswordUpdateOrigin = NavRoute.PasswordUpdateOrigin.PasswordRecovery,
    val isLoading: Boolean = false,
    val isPasswordMatch: Boolean = false,
    val showCancelUpdateDialog: Boolean = false,

    //ERRORS
    val passwordError: UserValidationError? = null,
    val confirmPasswordError: UserValidationError? = null,
)
