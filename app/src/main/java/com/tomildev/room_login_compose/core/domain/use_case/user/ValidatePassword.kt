package com.tomildev.room_login_compose.core.domain.use_case.user

import com.tomildev.room_login_compose.core.domain.model.user.ValidationError
import com.tomildev.room_login_compose.core.domain.model.user.ValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {

        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])")

        if (!passwordRegex.matches(password)) {
            return ValidationResult.Error(
                error = ValidationError.InvalidPassword
            )
        }

        if (password.length < 8) {
            return ValidationResult.Error(
                error = ValidationError.TooShort
            )
        }

        if (password.isBlank()) {
            return ValidationResult.Error(
                error = ValidationError.EmptyField
            )
        }

        return ValidationResult.Success
    }
}