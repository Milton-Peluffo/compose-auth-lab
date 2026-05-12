package com.tomildev.trakii.user

import com.tomildev.trakii.core.domain.model.user.UserValidationError
import com.tomildev.trakii.core.domain.model.user.UserValidationResult
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.ValidateNameUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the [ValidateNameUseCase] use case.
 *
 * This class verifies the name validation logic, ensuring that appropriate
 * [UserValidationResult]s and [UserValidationError]s are returned for various
 * scenarios such as empty inputs, names with invalid characters or spaces,
 * names that are too short, and successful validations.
 */
class ValidateNameUseCaseTest {

    val validateNameUseCase = ValidateNameUseCase()

    @Test
    fun `Empty name returns empty field error`(){

        val name = ""
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Name with only spaces returns empty field error`(){

        val name = "   "
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.EmptyField, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Name with spaces returns invalid name error`(){

        val name = " User "
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.InvalidName, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Name with special symbols returns invalid name error`(){

        val name = "user-?@"
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.InvalidName, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Name with fewer than 3 characters returns too short name error`(){

        val name = "Us"
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Error)
        assertEquals(UserValidationError.TooShortName, (result as UserValidationResult.Error).error)
    }

    @Test
    fun `Name with underscores returns success`(){

        val name = "user_"
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Success)
    }

    @Test
    fun `Name with numbers returns success`(){

        val name = "user72"
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Success)
    }

    @Test
    fun `Name with uppercases returns success`(){

        val name = "MyUser"
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Success)
    }

    @Test
    fun `Name with uppercases, numbers and underscore returns success`(){

        val name = "MyUser_72"
        val result = validateNameUseCase(name)

        assert(result is UserValidationResult.Success)
    }
}