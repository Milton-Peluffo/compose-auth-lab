package com.tomildev.trakii.core.domain.use_case.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidateName @Inject constructor() {

    operator fun invoke(name: String): UserValidationResult {
        val nameRegex = Regex("^[A-Za-z0-9 _]+$")

        if (name.isBlank()) {
            return UserValidationResult.Error(
                error = UserValidationError.EmptyField
            )
        }

        if (name.length < 3) {
            return UserValidationResult.Error(
                error = UserValidationError.TooShortName
            )
        }

        if (name.length > 20) {
            return UserValidationResult.Error(
                error = UserValidationError.TooLongName
            )
        }

        if (!nameRegex.matches(name)) {
            return UserValidationResult.Error(
                error = UserValidationError.InvalidName
            )
        }

        return UserValidationResult.Success
    }
}
