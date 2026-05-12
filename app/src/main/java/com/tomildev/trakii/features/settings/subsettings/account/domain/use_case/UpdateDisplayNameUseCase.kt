package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.settings.subsettings.account.domain.AccountSettingsRepository
import javax.inject.Inject

class UpdateDisplayNameUseCase @Inject constructor(
    private val repository: AccountSettingsRepository
) {
    suspend operator fun invoke(name: String): Result<Unit, DataError.Network> {
        return repository.updateDisplayName(name)
    }
}