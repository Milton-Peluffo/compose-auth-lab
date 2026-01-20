package com.tomildev.room_login_compose.features.auth.data.repository

import com.tomildev.room_login_compose.features.auth.data.local.dao.UserDao
import com.tomildev.room_login_compose.features.auth.data.local.entities.UserEntity
import com.tomildev.room_login_compose.features.auth.domain.model.User
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val userDao: UserDao) : AuthRepository {

    override suspend fun registerUser(user: User): Result<Unit> {
        return try {
            val entity = UserEntity(
                email = user.email,
                password = user.password
            )
            userDao.insertUser(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}