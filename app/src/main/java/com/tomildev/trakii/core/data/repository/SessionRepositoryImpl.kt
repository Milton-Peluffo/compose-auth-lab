package com.tomildev.trakii.core.data.repository

import com.tomildev.trakii.core.common.util.mappers.nowTimestamp
import com.tomildev.trakii.core.common.util.mappers.toDomainUser
import com.tomildev.trakii.core.common.util.mappers.toLocalProfileEntity
import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.data.local.dao.ProfileDao
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userPreferences: UserPreferences,
    private val profileDao: ProfileDao
) : SessionRepository {

    private var cachedUser: User? = null

    override fun getCachedUser(): User? = cachedUser

    override fun observeCurrentUserId(): Flow<String?> = userPreferences.currentUserId

    override suspend fun getCurrentUserId(): String? = userPreferences.getCurrentUserId()

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
                            val existingLocalUser = getLocalUser(authUser.id)
                            val localOnboarding = existingLocalUser?.onBoardingCompleted
                                ?: userPreferences.isOnboardingCompleted(authUser.id)
                            val localUser =
                                existingLocalUser ?: domainUser.copy(onBoardingCompleted = localOnboarding)

                            userPreferences.setCurrentUserId(localUser.id)
                            profileDao.upsertProfile(localUser.toLocalProfileEntity())
                            cachedUser = localUser
                            emit(SessionState.Authenticated(localUser))

                            try {
                                val profile = supabaseClient
                                    .from("profiles")
                                    .select { filter { eq("id", authUser.id) } }
                                    .decodeSingle<ProfileDto>()

                                if (profile.onboarding_completed) {
                                    profileDao.updateOnboardingCompleted(
                                        userId = authUser.id,
                                        completed = true,
                                        updatedAt = profile.updated_at ?: nowTimestamp()
                                    )
                                    userPreferences.setOnboardingCompleted(authUser.id, true)
                                    userPreferences.setOnboardingSyncPending(authUser.id, false)
                                } else if (localOnboarding) {
                                    try {
                                        supabaseClient.from("profiles")
                                            .update({ set("onboarding_completed", true) }) {
                                                filter { eq("id", authUser.id) }
                                            }
                                        userPreferences.setOnboardingSyncPending(authUser.id, false)
                                    } catch (_: Exception) {
                                        userPreferences.setOnboardingSyncPending(authUser.id, true)
                                    }
                                }

                                val finalUser = profile
                                    .toLocalProfileEntity(
                                        fallback = domainUser.copy(
                                            onBoardingCompleted = profile.onboarding_completed || localOnboarding
                                        )
                                    )
                                    .toDomainUser()
                                profileDao.upsertProfile(finalUser.toLocalProfileEntity())
                                if (finalUser != localUser) {
                                    cachedUser = finalUser
                                    emit(SessionState.Authenticated(finalUser))
                                }
                            } catch (_: Exception) {
                            }
                        }
                    }

                    is SessionStatus.NotAuthenticated -> {
                        flow {
                            val localUser = getLocalUser()
                            if (localUser != null) {
                                cachedUser = localUser
                                emit(SessionState.Authenticated(localUser))
                            } else {
                                cachedUser = null
                                emit(SessionState.Unauthenticated)
                            }
                        }
                    }

                    is SessionStatus.RefreshFailure -> {
                        flow {
                            val localUser = getLocalUser()
                            if (localUser != null) {
                                cachedUser = localUser
                                emit(SessionState.Authenticated(localUser))
                            } else {
                                emit(
                                    SessionState.Error(
                                        message = "The session could not be refreshed"
                                    )
                                )
                            }
                        }
                    }
                }
            }
            .catch {
                val localUser = getLocalUser()
                if (localUser != null) {
                    cachedUser = localUser
                    emit(SessionState.Authenticated(localUser))
                } else {
                    emit(
                        SessionState.Error(
                            it.message ?: "Session Error"
                        )
                    )
                }
            }
    }

    override suspend fun getCurrentUser(): User? {
        supabaseClient.auth.awaitInitialization()
        val authUser = supabaseClient.auth.currentUserOrNull()
            ?: return getLocalUser()
        val domainUser = authUser.toDomainUser()
        return try {
            val localOnboarding = getLocalUser(authUser.id)?.onBoardingCompleted
                ?: userPreferences.isOnboardingCompleted(authUser.id)
            val profile = supabaseClient
                .from("profiles")
                .select { filter { eq("id", authUser.id) } }
                .decodeSingle<ProfileDto>()

            if (profile.onboarding_completed) {
                profileDao.updateOnboardingCompleted(
                    userId = authUser.id,
                    completed = true,
                    updatedAt = profile.updated_at ?: nowTimestamp()
                )
                userPreferences.setOnboardingCompleted(authUser.id, true)
                userPreferences.setOnboardingSyncPending(authUser.id, false)
            }

            val finalUser = profile
                .toLocalProfileEntity(
                    fallback = domainUser.copy(
                        onBoardingCompleted = profile.onboarding_completed || localOnboarding
                    )
                )
                .toDomainUser()
            userPreferences.setCurrentUserId(finalUser.id)
            profileDao.upsertProfile(finalUser.toLocalProfileEntity())
            finalUser
        } catch (_: Exception) {
            val localUser = domainUser.copy(
                onBoardingCompleted = userPreferences.isOnboardingCompleted(authUser.id)
            )
            userPreferences.setCurrentUserId(localUser.id)
            profileDao.upsertProfile(localUser.toLocalProfileEntity())
            localUser
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
        userPreferences.clearLocalSession()
        supabaseClient.auth.signOut(SignOutScope.LOCAL)
    }

    private suspend fun getLocalUser(userId: String? = null): User? {
        val resolvedUserId = userId ?: userPreferences.getCurrentUserId() ?: return null
        return profileDao.getProfile(resolvedUserId)?.toDomainUser()
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
            createdAt = createdAt?.toString(),
            provider = "google",
            isEarlyUser = true,
            accountType = "free",
            updatedAt = nowTimestamp()
        )
    }
}
