package com.tomildev.room_login_compose.core.domain.use_case.user

import com.tomildev.room_login_compose.core.domain.model.user.UserValidationError
import com.tomildev.room_login_compose.core.domain.model.user.UserValidationResult
import javax.inject.Inject

class ValidateConfirmPassword @Inject constructor() {

    fun execute(password: String, confirmPassword: String): UserValidationResult {

        if (password != confirmPassword) {
            return UserValidationResult.Error(
                error = UserValidationError.PasswordDoNotMatch
            )
        }
        return UserValidationResult.Success
    }
}