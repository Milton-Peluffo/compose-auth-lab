package com.tomildev.room_login_compose.core.domain.use_case.user

import com.tomildev.room_login_compose.core.domain.model.user.ValidationError
import com.tomildev.room_login_compose.core.domain.model.user.ValidationResult
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    fun execute(email: String): ValidationResult {

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

        if (!emailRegex.matches(email)) {
            return ValidationResult.Error(
                error = ValidationError.InvalidEmail
            )
        }
        return ValidationResult.Success
    }
}