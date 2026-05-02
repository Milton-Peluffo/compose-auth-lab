package com.tomildev.trakii.features.auth.common.domain

import com.tomildev.trakii.core.data.remote.dto.ProfileDto
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface AuthUserRepository {
    suspend fun getProfileByEmail(email: String): Result<ProfileDto?, DataError.Network>
}