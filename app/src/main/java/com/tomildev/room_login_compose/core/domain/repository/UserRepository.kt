package com.tomildev.room_login_compose.core.domain.repository

import com.tomildev.room_login_compose.core.domain.model.User

interface UserRepository {

    suspend fun getUserByEmail(email: String): Result<User?>

}