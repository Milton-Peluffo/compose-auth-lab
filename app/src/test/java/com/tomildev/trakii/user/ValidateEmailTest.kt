package com.tomildev.trakii.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.core.domain.use_case.user.ValidateEmail
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the [ValidateEmail] use case.
 *
 * This class verifies the email validation logic, ensuring that appropriate
 * [UserValidationResult]s and [UserValidationError]s are returned for various
 * scenarios such as empty inputs, improperly formatted email addresses,
 * and successful validations.
 */

class ValidateEmailTest {

    private val validateEmail = ValidateEmail()

    @Test
    fun `Empty email, returns error empty field`() {

        val email = ""
        val result = validateEmail(email)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `email with only spaces returns empty field error`() {

        val email = "     "
        val result = validateEmail(email)

        assert(result is UserValidationResult.Error)
        assertEquals(
            UserValidationError.EmptyField,
            (result as UserValidationResult.Error).error
        )
    }

    @Test
    fun `email without at returns invalid email error`(){

        val email = "examplegmail.com"
        val result = validateEmail(email)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.InvalidEmail, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `email with spaces returns invalid email error`(){

        val email = " examplegmail.com "
        val result = validateEmail(email)
        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.InvalidEmail, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `email with double at returns invalid email error`() {

        val email = "test@@gmail.com"
        val result = validateEmail(email)

        assert(result is UserValidationResult.Error)
        assertEquals(
            UserValidationError.InvalidEmail,
            (result as UserValidationResult.Error).error
        )
    }

    @Test
    fun `email with one letter domain returns invalid email error`() {

        val email = "test@gmail.c"
        val result = validateEmail(email)

        assert(result is UserValidationResult.Error)
        assertEquals(
            UserValidationError.InvalidEmail,
            (result as UserValidationResult.Error).error
        )
    }

    @Test
    fun `email with multiple domain levels returns success`() {

        val email = "hello@university.edu.com"
        val result = validateEmail(email)

        assert(result is UserValidationResult.Success)
    }

    @Test
    fun `email with uppercase letters returns success`() {

        val email = "Example@Gmail.COM"
        val result = validateEmail(email)

        assert(result is UserValidationResult.Success)
    }

    @Test
    fun `correct email returns sucess`(){

        val email = "example@gmail.com"
        val result = validateEmail(email)
        assert(result is UserValidationResult.Success)
    }
}
