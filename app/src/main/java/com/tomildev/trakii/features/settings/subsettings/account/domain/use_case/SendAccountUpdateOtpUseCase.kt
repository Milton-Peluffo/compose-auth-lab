package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.settings.subsettings.account.domain.AccountSettingsRepository
import javax.inject.Inject

class SendAccountUpdateOtpUseCase @Inject constructor(
    private val repository: AccountSettingsRepository
) {
    suspend operator fun invoke(email: String): Result<Unit, DataError.Network> {
        return repository.sendAccountUpdateOtp(email)
    }
}
