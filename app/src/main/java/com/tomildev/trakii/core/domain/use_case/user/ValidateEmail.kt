package com.tomildev.trakii.core.domain.use_case.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    fun execute(email: String): UserValidationResult {

        val emailRegex = Regex(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$"
        )

        if (email.isBlank())
            return UserValidationResult.Error(
                error = UserValidationError.EmptyField
            )

        if (!emailRegex.matches(email)) {
            return UserValidationResult.Error(
                error = UserValidationError.InvalidEmail
            )
        }
        return UserValidationResult.Success
    }
}