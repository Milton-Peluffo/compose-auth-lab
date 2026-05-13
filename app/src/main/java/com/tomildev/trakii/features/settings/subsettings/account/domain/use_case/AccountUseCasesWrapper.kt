package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

import com.tomildev.trakii.features.settings.main_settings.domain.use_case.LogoutUseCase

data class AccountUseCasesWrapper(
    val updateDisplayName: UpdateDisplayNameUseCase,
    val validateNameUseCase: ValidateNameUseCase,
    val logout: LogoutUseCase
)