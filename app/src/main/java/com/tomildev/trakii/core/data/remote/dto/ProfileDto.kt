package com.tomildev.trakii.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,
    val onboarding_completed: Boolean = false
)