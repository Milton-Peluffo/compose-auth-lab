package com.tomildev.trakii.core.domain.use_case.user

import javax.inject.Inject

data class UserValidationUseCases @Inject constructor(
    val validateName: ValidateName,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword
)