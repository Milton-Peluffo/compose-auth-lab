package com.tomildev.trakii.core.data.repository

import com.tomildev.trakii.core.domain.model.user.User
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : SessionRepository {

    override fun observeSession(): Flow<SessionState> {
        return supabaseClient.auth.sessionStatus
            .map { status ->
                when (status) {
                    is SessionStatus.Initializing -> SessionState.Loading
                    is SessionStatus.Authenticated -> {
                        val user = status.session.user ?: supabaseClient.auth.currentUserOrNull()
                        user?.toDomainUser()?.let(SessionState::Authenticated)
                            ?: SessionState.Unauthenticated
                    }
                    is SessionStatus.NotAuthenticated -> SessionState.Unauthenticated
                    is SessionStatus.RefreshFailure -> SessionState.Error(
                        message = "The session could not be refreshed"
                    )
                }
            }
            .catch { emit(SessionState.Error(it.message ?: "Session Error")) }
    }

    override suspend fun getCurrentUser(): User? {
        supabaseClient.auth.awaitInitialization()
        return supabaseClient.auth.currentUserOrNull()?.toDomainUser()
    }

    override suspend fun refreshSession(): Result<Unit> {
        return runCatching {
            supabaseClient.auth.refreshCurrentSession()
        }
    }

    override suspend fun logout() {
        supabaseClient.auth.signOut(SignOutScope.LOCAL)
    }

    private fun UserInfo.toDomainUser(): User {
        val displayName = userMetadata
            ?.get("display_name")
            ?.jsonPrimitive
            ?.contentOrNull
            ?: userMetadata
                ?.get("full_name")
                ?.jsonPrimitive
                ?.contentOrNull

        return User(
            id = id,
            name = displayName.orEmpty(),
            email = email.orEmpty(),
            createdAt = createdAt?.toString()
        )
    }
}