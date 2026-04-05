package com.tomildev.room_login_compose.features.auth.signup.presentation

import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError

data class SignUpUiState(
    //USER DATA
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    // STATES / VALIDATORS
    val showSuccessDialog: Boolean = false,
    val isLoading: Boolean = false,

    //ERRORS
    val networkError: DataError? = null,
    val errorMessage: String? = null,
    val nameError: UserValidationError? = null,
    val emailError: UserValidationError? = null,
    val passwordError: UserValidationError? = null,
    val passwordConfirmError: UserValidationError? = null,
)