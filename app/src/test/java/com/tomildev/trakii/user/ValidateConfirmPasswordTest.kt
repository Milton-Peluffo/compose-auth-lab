package com.tomildev.trakii.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.ValidateConfirmPassword
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the [ValidateConfirmPassword] use case.
 *
 * This class verifies the logic for comparing a password with its confirmation field,
 * ensuring that appropriate [UserValidationResult]s and [UserValidationError]s are
 * returned for various scenarios such as empty inputs, mismatching passwords,
 * and successful matches.
 */

class ValidateConfirmPasswordTest {

    val validateConfirmPassword = ValidateConfirmPassword()

    @Test
    fun `Empty confirm password returns empty field error`() {
        val password = ""
        val confirmPassword = ""
        val result = validateConfirmPassword(password, confirmPassword)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Confirm password with only spaces returns empty field error`() {
        val password = ""
        val confirmPassword = "    "
        val result = validateConfirmPassword(password, confirmPassword)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `When confirm password do not match returns PasswordDoNotMatch error`() {
        val password = "@1234"
        val confirmPassword = "@A12373"
        val result = validateConfirmPassword(password, confirmPassword)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.PasswordDoNotMatch, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `When confirm password matches the password, returns success`() {
        val password = "@54321"
        val confirmPassword = "@54321"
        val result = validateConfirmPassword(password, confirmPassword)
        assert(result is UserValidationResult.Success)
    }
}