package com.tomildev.trakii.features.auth.signup.domain

import com.tomildev.trakii.core.data.remote.dto.ProfileDto
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface SignUpRepository {
    suspend fun getProfileByEmail(email: String): Result<ProfileDto?, DataError.Network>
    suspend fun signUpWithOtp(email: String): Result<Unit, DataError.Network>
    suspend fun signInWithOtp(email: String): Result<Unit, DataError.Network>
    suspend fun completeRegistration(name: String, password: String): Result<Unit, DataError.Network>
}
