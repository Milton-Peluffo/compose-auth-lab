package com.tomildev.room_login_compose.core.domain.repository

import com.tomildev.room_login_compose.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByEmail(email: String): Result<User?>
    suspend fun getUserById(id: Int): Result<User?>
    suspend fun saveUserSession(userId: Int)
    suspend fun closeUserSession()
    fun getCurrentUser(): Flow<User?>
}