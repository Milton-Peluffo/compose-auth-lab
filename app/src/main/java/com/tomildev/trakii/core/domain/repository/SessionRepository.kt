package com.tomildev.trakii.core.domain.repository

import com.tomildev.trakii.core.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun observeSession(): Flow<SessionState>
    suspend fun getCurrentUser(): User?
    suspend fun refreshSession(): Result<Unit>
    suspend fun logout()
}

sealed class SessionState {
    object Loading : SessionState()
    data class Authenticated(val user: User) : SessionState()
    object Unauthenticated : SessionState()
    data class Error(val message: String) : SessionState()
}