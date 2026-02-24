package com.tomildev.room_login_compose.core.domain.model

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val createdAt: String? = null,
)