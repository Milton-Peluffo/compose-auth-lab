package com.tomildev.trakii.features.settings.sub_settings.account.presentation

import com.tomildev.trakii.core.domain.model.error.DataError

sealed class AccountSettingsUiEvent {
    data class Error(val error: DataError) : AccountSettingsUiEvent()
    data class Warning(val error: DataError) : AccountSettingsUiEvent()
}