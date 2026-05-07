package com.tomildev.trakii.core.domain.repository

import com.tomildev.trakii.core.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByEmail(email: String): Result<User?>
    suspend fun getUserById(id: String): Result<User?>
    suspend fun deleteUserById(): Result<Unit>

    //------ THEME -------
    fun isDarkThemeEnabled(): Flow<Boolean>
    suspend fun toggleTheme(isEnabled: Boolean)
}
