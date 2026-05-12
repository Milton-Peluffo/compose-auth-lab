package com.tomildev.trakii.features.auth.signin.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface OAuthRepository {
    suspend fun authWithGoogle(idToken: String): Result<Unit, DataError.Network>
}