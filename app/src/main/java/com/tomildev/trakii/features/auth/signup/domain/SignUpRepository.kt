package com.tomildev.trakii.features.auth.signup.domain

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.User
import com.tomildev.trakii.core.domain.util.Result

interface SignUpRepository {
    suspend fun signUp(user: User, password: String): Result<Unit, DataError.Network>
    suspend fun sendOtp(email: String): Result<Unit, DataError.Network>
    suspend fun completeRegistration(name: String, password: String): Result<Unit, DataError.Network>
}