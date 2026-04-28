package com.tomildev.trakii.features.auth.signin.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface SignInRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<Unit, DataError.Network>
}