package com.tomildev.trakii.features.auth.signup.presentation.complete_signup

import com.tomildev.trakii.core.domain.model.user.UserValidationError

data class CompleteSignUpUiState(
    //USER DATA
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    // STATES / VALIDATORS
    val isLoading: Boolean = false,
    val isPasswordMatch: Boolean = false,

    //ERRORS
    val nameError: UserValidationError? = null,
    val passwordError: UserValidationError? = null,
    val confirmPasswordError: UserValidationError? = null,
)