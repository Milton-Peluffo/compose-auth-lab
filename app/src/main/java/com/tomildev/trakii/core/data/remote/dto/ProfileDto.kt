package com.tomildev.trakii.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,
    val email: String? = null,
    val display_name: String? = null,
    val avatar_url: String? = null,
    val provider: String? = null,
    val is_early_user: Boolean = true,
    val account_type: String = "free",
    val onboarding_completed: Boolean = false,
    val created_at: String? = null,
    val updated_at: String? = null
)
