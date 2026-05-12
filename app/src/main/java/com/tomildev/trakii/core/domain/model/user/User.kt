package com.tomildev.trakii.core.domain.model.user

data class User(
    val id: String,
    val displayName: String,
    val email: String,
    val onBoardingCompleted: Boolean,
    val createdAt: String? = null,
)