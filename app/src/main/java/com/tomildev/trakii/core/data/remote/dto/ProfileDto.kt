package com.tomildev.trakii.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("provider")
    val provider: String? = null,
    @SerialName("is_profile_complete")
    val isProfileComplete: Boolean = false
)