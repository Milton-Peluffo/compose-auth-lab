package com.tomildev.room_login_compose.features.auth.data.remote.dto

import kotlinx.serialization.SerialName

data class ProfileDto(
    @SerialName("id") val id: String,
    @SerialName("display_name") val displayName: String
)