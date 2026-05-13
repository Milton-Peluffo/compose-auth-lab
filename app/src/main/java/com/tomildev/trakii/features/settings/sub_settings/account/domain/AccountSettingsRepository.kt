package com.tomildev.trakii.features.settings.sub_settings.account.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface AccountSettingsRepository {
    suspend fun updateDisplayName(name: String): Result<Unit, DataError.Network>
}