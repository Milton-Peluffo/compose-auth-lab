package com.tomildev.room_login_compose.core.data.repository

import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.domain.model.User
import com.tomildev.room_login_compose.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {

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