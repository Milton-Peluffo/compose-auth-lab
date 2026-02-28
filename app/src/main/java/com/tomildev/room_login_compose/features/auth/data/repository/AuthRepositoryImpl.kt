package com.tomildev.room_login_compose.features.auth.data.repository

import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.domain.model.user.User
import com.tomildev.room_login_compose.features.auth.data.remote.dto.ProfileDto
import com.tomildev.room_login_compose.features.auth.data.remote.dto.SignUpRequestDto
import com.tomildev.room_login_compose.features.auth.data.remote.service.AuthService
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val authService: AuthService
) : AuthRepository {

    override suspend fun registerUser(user: User, password: String): Result<Unit> {
        return try {
            val authResponse = authService.signUp(
                request = SignUpRequestDto(
                    email = user.email,
                    password = password,
                    data = mapOf("display_name" to user.name)
                )
            )
            authService.createProfile(
                profile = ProfileDto(
                    id = authResponse.user.id,
                    displayName = user.name
                )
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}