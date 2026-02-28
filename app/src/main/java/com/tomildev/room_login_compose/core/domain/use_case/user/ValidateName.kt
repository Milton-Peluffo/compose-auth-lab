package com.tomildev.room_login_compose.core.domain.use_case.user

import com.tomildev.room_login_compose.core.domain.model.user.ValidationError
import com.tomildev.room_login_compose.core.domain.model.user.ValidationResult
import javax.inject.Inject

class ValidateName @Inject constructor() {

    val usernameRegex = Regex("^[A-Za-z0-9_]+$")

    fun execute(name: String) : ValidationResult {
        if (!usernameRegex.matches(name)) {
            ValidationResult.Error(
                error = ValidationError.InvalidName
            )
        }

        if (name.length <= 3) {
            ValidationResult.Error(
                error = ValidationError.TooShort
            )
        }

        return ValidationResult.Success
    }
}