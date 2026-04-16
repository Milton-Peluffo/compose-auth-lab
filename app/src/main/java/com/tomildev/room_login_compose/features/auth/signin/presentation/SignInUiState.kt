package com.tomildev.room_login_compose.features.auth.signin.presentation

import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError

data class SignInUiState(
    //USER DATA
    val email: String = "",
    val password: String = "",

    // STATES / VALIDATORS
    val isLoading: Boolean = false,

    //ERRORS
    val networkError: DataError? = null,
    val errorMessage: String? = null,
    val emailError: UserValidationError? = null,
    val passwordError: UserValidationError? = null,
)