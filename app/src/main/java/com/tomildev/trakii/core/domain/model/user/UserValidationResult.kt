package com.tomildev.trakii.core.domain.model.user

sealed class UserValidationResult {
    object Success : UserValidationResult()
    data class Error(val error: UserValidationError) : UserValidationResult()
}

sealed class UserValidationError {
    object EmptyField : UserValidationError()
    object TooShortName : UserValidationError()
    object TooLongName : UserValidationError()
    object InvalidName : UserValidationError()
}
