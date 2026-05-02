package com.tomildev.trakii.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.ValidatePassword
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the [ValidatePassword] use case.
 *
 * This class verifies the password validation logic, ensuring that appropriate
 * [UserValidationResult]s and [UserValidationError]s are returned for various
 * scenarios such as empty inputs, passwords that are too short, missing required
 * character types, and successful validations.
 */

class ValidatePasswordTest {

    val validatePassword = ValidatePassword()

    @Test
    fun `Empty password returns empty field error`(){
        val password = ""
        val result = validatePassword(password)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Password with only spaces returns empty field error`(){
        val password = "   "
        val result = validatePassword(password)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Password with only numbers returns invalid password error`(){
        val password = "12345678"
        val result = validatePassword(password)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.InvalidPassword, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Password with less than 8 characters returns password too short error`(){
        val password = "@1234"
        val result = validatePassword(password)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.TooShortPassword, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Password with special character, letters and numbers returns success`(){
        val password = "@Pass234"
        val result = validatePassword(password)
        assert(result is UserValidationResult.Success)
    }
}