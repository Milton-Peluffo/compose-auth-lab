package com.tomildev.trakii.core.domain.use_case.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidateConfirmPassword @Inject constructor() {

    fun execute(password: String, confirmPassword: String): UserValidationResult {

        if (confirmPassword.isBlank())
            return UserValidationResult.Error(
                error = UserValidationError.EmptyField
            )

        if (password != confirmPassword) {
            return UserValidationResult.Error(
                error = UserValidationError.PasswordDoNotMatch
            )
        }
        return UserValidationResult.Success
    }
}