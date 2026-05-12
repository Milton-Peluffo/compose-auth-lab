package com.tomildev.trakii.features.settings.subsettings.account.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

sealed class AccountSettingsUiEvent {
    data object NavigateToSignIn : AccountSettingsUiEvent()
    data class Error(val error: DataError) : AccountSettingsUiEvent()
    data class Warning(val error: DataError) : AccountSettingsUiEvent()
}
