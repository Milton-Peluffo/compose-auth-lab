package com.tomildev.room_login_compose.features.auth.data.repository

import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.data.local.entities.UserEntity
import com.tomildev.room_login_compose.core.domain.model.User
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val userDao: UserDao) : AuthRepository {

    override suspend fun registerUser(user: User): Result<Unit> {
        return try {
            val entity = UserEntity(
                name = user.name,
                phone = user.phone,
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