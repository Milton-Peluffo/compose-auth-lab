package com.tomildev.room_login_compose.features.auth.data.remote.service

import com.tomildev.room_login_compose.features.auth.data.remote.dto.ProfileDto
import com.tomildev.room_login_compose.features.auth.data.remote.dto.SignUpRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/v1/signup")
    suspend fun signUp(
        @Body request: SignUpRequestDto
    )

    @POST("rest/v1/profiles")
    suspend fun createProfile(
        @Body profile: ProfileDto
    )
}