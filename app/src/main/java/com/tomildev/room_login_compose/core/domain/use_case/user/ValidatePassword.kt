package com.tomildev.room_login_compose.core.domain.use_case.user

import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): UserValidationResult {

        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).+$")

        if (password.isBlank()) {
            return UserValidationResult.Error(
                error = UserValidationError.EmptyField
            )
        }

        if (password.length < 8) {
            return UserValidationResult.Error(
                error = UserValidationError.TooShortPassword
            )
        }

        if (!passwordRegex.matches(password)) {
            return UserValidationResult.Error(
                error = UserValidationError.InvalidPassword
            )
        }

        return UserValidationResult.Success
    }
}