package com.tomildev.room_login_compose.core.domain.model

data class User(
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)