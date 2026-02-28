package com.tomildev.room_login_compose.core.domain.model.user

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val error: ValidationError) : ValidationResult()
}

sealed class ValidationError {
    object EmptyField : ValidationError()
    object TooShort : ValidationError()
    object InvalidName : ValidationError()
    object InvalidEmail : ValidationError()
    object InvalidPassword : ValidationError()
}