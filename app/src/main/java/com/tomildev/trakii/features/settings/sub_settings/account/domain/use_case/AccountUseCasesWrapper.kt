package com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case

import com.tomildev.trakii.features.settings.main_settings.domain.use_case.LogoutUseCase

data class AccountUseCasesWrapper(
    val updateDisplayName: UpdateDisplayNameUseCase,
    val validateNameUseCase: ValidateNameUseCase,
    val logout: LogoutUseCase
)