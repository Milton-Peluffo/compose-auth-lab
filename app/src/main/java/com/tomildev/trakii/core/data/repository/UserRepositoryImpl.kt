package com.tomildev.trakii.core.data.repository

import com.tomildev.trakii.core.data.dao.UserDao
import com.tomildev.trakii.core.data.local.entities.UserEntity
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.model.user.User
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences,
    private val sessionRepository: SessionRepository
) : UserRepository {

    private fun UserEntity.toDomain() = User(
        id = id,
        name = name,
        email = email,
    )

    override suspend fun getUserByEmail(email: String): Result<User?> {
        return try {
            val entity = userDao.getUserByEmail(email)
            Result.success(entity?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(id: String): Result<User?> {
        return try {
            val entity = userDao.getUserById(id)
            Result.success(entity?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //------ THEME -------

    override fun isDarkThemeEnabled(): Flow<Boolean> = userPreferences.isDarkMode
    override suspend fun toggleTheme(isEnabled: Boolean) {
        userPreferences.toggleDarkMode(isDark = isEnabled)
    }

    override suspend fun deleteUserById(): Result<Unit> {
        val currentUser = sessionRepository.getCurrentUser()
            ?: return Result.failure(Exception("User not found"))

        return try {
            userDao.deleteUserById(currentUser.id)
            sessionRepository.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
