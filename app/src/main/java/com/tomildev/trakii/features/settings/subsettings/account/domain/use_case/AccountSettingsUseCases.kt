package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

import com.tomildev.trakii.core.domain.use_case.session.LogoutUseCase

data class AccountSettingsUseCases(
    val sendAccountUpdateOtp: SendAccountUpdateOtpUseCase,
    val updateDisplayName: UpdateDisplayNameUseCase,
    val logout: LogoutUseCase
)
