package com.tomildev.trakii.features.settings.subsettings.account.presentation

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.navigation.NavRoute

sealed class AccountSettingsUiEvent {
    data object NavigateToSignIn : AccountSettingsUiEvent()
    data class NavigateToOtp(
        val email: String,
        val purpose: NavRoute.OtpPurpose
    ) : AccountSettingsUiEvent()
    data class Error(val error: DataError) : AccountSettingsUiEvent()
    data class Warning(val error: DataError) : AccountSettingsUiEvent()
}
