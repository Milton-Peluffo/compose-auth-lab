package com.tomildev.trakii.core.domain.model.user

data class User(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: String? = null,
)