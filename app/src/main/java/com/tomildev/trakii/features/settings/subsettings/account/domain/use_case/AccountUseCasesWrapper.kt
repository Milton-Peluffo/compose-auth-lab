package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

data class AccountUseCasesWrapper(
    val updateDisplayName: UpdateDisplayNameUseCase,
    val validateNameUseCase: ValidateNameUseCase,
    val logout: LogoutUseCase
)