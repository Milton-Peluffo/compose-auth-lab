package com.tomildev.room_login_compose.features.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    val email: String,
    val password: String,
    val data: Map<String, String>
)