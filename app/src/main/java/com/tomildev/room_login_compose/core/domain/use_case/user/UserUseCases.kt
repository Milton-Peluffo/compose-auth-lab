package com.tomildev.room_login_compose.core.domain.use_case.user

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val validatePassword: ValidatePassword,
    val validateEmail: ValidateEmail
)