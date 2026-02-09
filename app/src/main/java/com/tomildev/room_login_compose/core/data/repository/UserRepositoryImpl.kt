package com.tomildev.room_login_compose.core.data.repository

import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.data.local.entities.UserEntity
import com.tomildev.room_login_compose.core.data.session.SessionManager
import com.tomildev.room_login_compose.core.domain.model.User
import com.tomildev.room_login_compose.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) : UserRepository {

    private fun UserEntity.toDomain() = User(
        id = id,
        name = name,
        phone = phone,
        email = email,
        password = password
    )

    override suspend fun getUserByEmail(email: String): Result<User?> {
        return try {
            val entity = userDao.getUserByEmail(email)
            Result.success(entity?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(id: Int): Result<User?> {
        return try {
            val entity = userDao.getUserById(id)
            Result.success(entity?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Flow<User?> = flow {
        sessionManager.userId.collect { id ->
            if (id != -1) {
                val entity = userDao.getUserById(id)
                emit(entity?.toDomain())
            } else {
                emit(null)
            }
        }
    }

    override suspend fun saveUserSession(userId: Int) {
        sessionManager.saveSession(userId)
    }

    override suspend fun closeUserSession() {
        sessionManager.logOut()
    }

    override suspend fun deleteUserById(): Result<Unit> {
        val userId = sessionManager.userId.first()
        if (userId != -1) {
            return try {
                userDao.deleteUserById(userId)
                sessionManager.logOut()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
        return Result.failure(Exception("User not found"))
    }
}