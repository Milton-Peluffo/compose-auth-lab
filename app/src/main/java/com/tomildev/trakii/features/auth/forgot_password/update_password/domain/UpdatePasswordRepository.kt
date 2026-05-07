package com.tomildev.trakii.features.auth.forgot_password.update_password.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface UpdatePasswordRepository {
    suspend fun updatePassword(password: String): Result<Unit, DataError.Network>
}
