package com.tomildev.trakii.core.domain.model.user

data class User(
    val id: String,
    val displayName: String,
    val avatarUrl: String,
    val email: String,
    val onBoardingCompleted: Boolean,
    val createdAt: String? = null,
    val provider: String = "unknown",
    val isEarlyUser: Boolean = true,
    val accountType: String = "free",
    val updatedAt: String? = null
)
