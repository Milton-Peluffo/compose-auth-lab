package com.tomildev.room_login_compose.features.auth.signup.domain

import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.model.user.User
import com.tomildev.room_login_compose.core.domain.util.Result

interface SignUpRepository {

    suspend fun signUp(user: User, password: String): Result<Unit, DataError.Network>
}