package com.tomildev.trakii.core.domain.repository

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface PasswordRepository {
    suspend fun updatePassword(password: String): Result<Unit, DataError.Network>
}
