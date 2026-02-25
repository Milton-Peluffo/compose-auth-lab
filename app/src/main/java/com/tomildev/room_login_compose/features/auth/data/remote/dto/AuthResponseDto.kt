package com.tomildev.room_login_compose.features.auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("user") val user: UserDto
)

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("created_at") val createdAt: String
)