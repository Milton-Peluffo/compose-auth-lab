package com.tomildev.room_login_compose.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey
    val uid: String,
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val createdAt: String? = null
)