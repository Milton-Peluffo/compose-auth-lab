package com.tomildev.room_login_compose.features.auth.domain.repository

import com.tomildev.room_login_compose.core.domain.model.User

interface AuthRepository {

    suspend fun registerUser(user: User): Result<Unit>
}