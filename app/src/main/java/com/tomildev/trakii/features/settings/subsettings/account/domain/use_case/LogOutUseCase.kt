package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

import com.tomildev.trakii.features.settings.subsettings.account.domain.AccountSettingsRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AccountSettingsRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
