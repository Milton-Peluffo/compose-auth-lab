package com.tomildev.trakii.core.common.presentation.password_update

import com.tomildev.trakii.core.domain.model.error.DataError

sealed interface UpdatePasswordUiEvent {
    data class NavigateToSignIn(val showPasswordUpdatedSnackbar: Boolean) : UpdatePasswordUiEvent
    data object NavigateToAccountSettings : UpdatePasswordUiEvent
    data class Error(val error: DataError) : UpdatePasswordUiEvent
    data class Warning(val error: DataError) : UpdatePasswordUiEvent
}
