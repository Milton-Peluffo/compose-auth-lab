package com.tomildev.trakii.core.data.repository

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.data.remote.dto.ProfileDto
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.model.user.User
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import com.tomildev.trakii.core.domain.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userPreferences: UserPreferences
) : SessionRepository {

    private var cachedUser: User? = null

    override fun getCachedUser(): User? = cachedUser

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSession(): Flow<SessionState> {
        return supabaseClient.auth.sessionStatus
            .flatMapLatest { status ->
                when (status) {
                    is SessionStatus.Initializing -> {
                        flow {
                            emit(SessionState.Loading)
                        }
                    }

                    is SessionStatus.Authenticated -> {
                        flow {
                            val authUser = status.session.user
                                ?: supabaseClient.auth.currentUserOrNull()
                            if (authUser == null) {
                                emit(SessionState.Unauthenticated)
                                return@flow
                            }

                            val domainUser = authUser.toDomainUser()

                            val finalUser = try {
                                val profile = supabaseClient
                                    .from("profiles")
                                    .select { filter { eq("id", authUser.id) } }
                                    .decodeSingle<ProfileDto>()

                                val localOnboarding = userPreferences.onboardingCompleted.first()

                                if (localOnboarding && !profile.onboarding_completed) {
                                    try {
                                        supabaseClient.from("profiles")
                                            .update({ set("onboarding_completed", true) }) {
                                                filter { eq("id", authUser.id) }
                                            }
                                    } catch (_: Exception) {
                                    }
                                }

                                domainUser.copy(
                                    onBoardingCompleted = profile.onboarding_completed || localOnboarding
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                val localOnboarding = userPreferences.onboardingCompleted.first()
                                domainUser.copy(onBoardingCompleted = localOnboarding)
                            }

                            cachedUser = finalUser
                            emit(SessionState.Authenticated(finalUser))
                        }
                    }

                    is SessionStatus.NotAuthenticated -> {
                        flow {
                            cachedUser = null
                            emit(SessionState.Unauthenticated)
                        }
                    }

                    is SessionStatus.RefreshFailure -> {
                        flow {
                            emit(
                                SessionState.Error(
                                    message = "The session could not be refreshed"
                                )
                            )
                        }
                    }
                }
            }
            .catch {
                emit(
                    SessionState.Error(
                        it.message ?: "Session Error"
                    )
                )
            }
    }

    override suspend fun getCurrentUser(): User? {
        supabaseClient.auth.awaitInitialization()
        val authUser = supabaseClient.auth.currentUserOrNull()
            ?: return null
        val domainUser = authUser.toDomainUser()
        return try {
            val profile = supabaseClient
                .from("profiles")
                .select { filter { eq("id", authUser.id) } }
                .decodeSingle<ProfileDto>()
            domainUser.copy(
                onBoardingCompleted = profile.onboarding_completed
            )
        } catch (e: Exception) {
            e.printStackTrace()
            domainUser
        }
    }

    override suspend fun refreshSession(): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.refreshCurrentSession()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }

    override suspend fun logOut() {
        cachedUser = null
        supabaseClient.auth.signOut(SignOutScope.LOCAL)
    }

    private fun UserInfo.toDomainUser(): User {
        val displayName = userMetadata
            ?.get("full_name")
            ?.jsonPrimitive
            ?.contentOrNull

        val avatarUrl = userMetadata
            ?.get("avatar_url")
            ?.jsonPrimitive
            ?.contentOrNull

        return User(
            id = id,
            displayName = displayName.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            email = email.orEmpty(),
            onBoardingCompleted = false,
            createdAt = createdAt?.toString()
        )
    }
}