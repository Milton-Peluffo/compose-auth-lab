package com.tomildev.trakii.features.onboarding.data.repository

import com.tomildev.trakii.core.common.util.mappers.nowTimestamp
import com.tomildev.trakii.core.data.local.dao.ProfileDao
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.onboarding.domain.repository.OnboardingRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnBoardingRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userPreferences: UserPreferences,
    private val profileDao: ProfileDao
) : OnboardingRepository {

    override suspend fun completeOnboarding(): Result<Unit, DataError.Network> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
            ?: userPreferences.currentUserId.first()
            ?: return Result.Success(Unit)

        val updatedAt = nowTimestamp()
        profileDao.updateOnboardingCompleted(
            userId = userId,
            completed = true,
            updatedAt = updatedAt
        )
        userPreferences.setOnboardingCompleted(userId, true)
        try {
            supabaseClient.from("profiles").update(
                {
                    set("onboarding_completed", true)
                    set("updated_at", updatedAt)
                }
            ) {
                filter {
                    eq("id", userId)
                }
            }
            userPreferences.setOnboardingSyncPending(userId, false)
        } catch (_: Exception) {
            userPreferences.setOnboardingSyncPending(userId, true)
        }
        return Result.Success(Unit)
    }
}