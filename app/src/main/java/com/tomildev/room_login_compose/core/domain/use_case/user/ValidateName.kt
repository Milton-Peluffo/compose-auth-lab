package com.tomildev.room_login_compose.core.domain.use_case.user

import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidateName @Inject constructor() {

    fun execute(name: String): UserValidationResult {

        val usernameRegex = Regex("^[A-Za-z0-9_]+$")

        if (name.isBlank())
            UserValidationResult.Error(
                error = UserValidationError.EmptyField
            )

        if (!usernameRegex.matches(name)) {
            UserValidationResult.Error(
                error = UserValidationError.InvalidName
            )
        }

        if (name.length <= 3) {
            UserValidationResult.Error(
                error = UserValidationError.TooShortName
            )
        }

        return UserValidationResult.Success
    }
}