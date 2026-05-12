package com.tomildev.trakii.core.domain.repository

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.User
import com.tomildev.trakii.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun observeSession(): Flow<SessionState>
    fun getCachedUser(): User?
    suspend fun getCurrentUser(): User?
    suspend fun refreshSession(): Result<Unit, DataError.Network>
    suspend fun logOut()
}

sealed class SessionState {
    object Loading : SessionState()
    data class Authenticated(val user: User) : SessionState()
    object Unauthenticated : SessionState()
    data class Error(val message: String) : SessionState()
}