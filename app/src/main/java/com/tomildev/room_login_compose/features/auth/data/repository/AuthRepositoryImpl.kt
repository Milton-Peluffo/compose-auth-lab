package com.tomildev.room_login_compose.features.auth.data.repository

import com.tomildev.room_login_compose.features.auth.data.local.dao.UserDao
import com.tomildev.room_login_compose.features.auth.data.local.entities.UserEntity
import com.tomildev.room_login_compose.features.auth.domain.model.User
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

    override suspend fun getUserByEmail(email: String): Result<User?> {
        return try {
            val entity = userDao.getUserByEmail(email)
            if (entity != null) {
                val user = User(
                    name = entity.name,
                    phone = entity.phone,
                    email = entity.email,
                    password = entity.password
                )
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}