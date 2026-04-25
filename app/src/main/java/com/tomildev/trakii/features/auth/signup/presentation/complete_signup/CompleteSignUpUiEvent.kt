package com.tomildev.trakii.features.auth.signup.presentation.complete_signup

import com.tomildev.trakii.core.domain.model.error.DataError

sealed interface CompleteSignUpUiEvent {
    data object Success : CompleteSignUpUiEvent
    data class Error(val error: DataError) : CompleteSignUpUiEvent
    data class Warning(val error: DataError) : CompleteSignUpUiEvent
}