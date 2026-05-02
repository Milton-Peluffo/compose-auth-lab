package com.tomildev.trakii.features.auth.forgot_password.update_password.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

sealed interface UpdatePasswordUiEvent {
    data object Success : UpdatePasswordUiEvent
    data class Error(val error: DataError) : UpdatePasswordUiEvent
    data class Warning(val error: DataError) : UpdatePasswordUiEvent
}