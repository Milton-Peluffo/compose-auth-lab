package com.tomildev.trakii.core.domain.use_case.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidateName @Inject constructor() {

    fun execute(name: String): UserValidationResult {

        val usernameRegex = Regex("^[A-Za-z0-9_]+$")

        if (name.isBlank())
            return UserValidationResult.Error(
                error = UserValidationError.EmptyField
            )

        if (!usernameRegex.matches(name)) {
            return UserValidationResult.Error(
                error = UserValidationError.InvalidName
            )
        }

        if (name.length < 3) {
            return UserValidationResult.Error(
                error = UserValidationError.TooShortName
            )
        }

        return UserValidationResult.Success
    }
}