package com.tomildev.trakii.features.onboarding.data.repository

import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.onboarding.domain.repository.OnboardingRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnBoardingRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userPreferences: UserPreferences
) : OnboardingRepository {

    override suspend fun completeOnboarding(): Result<Unit, DataError.Network> {
        userPreferences.setOnboardingCompleted(true)
        try {
            val user = supabaseClient.auth.currentUserOrNull()
            if (user != null) {

                supabaseClient.from("profiles").update(
                    {
                        set("onboarding_completed", true)
                    }
                ) {
                    filter {
                        eq("id", user.id)
                    }
                }
            }
        } catch (_: Exception) {
        }
        return Result.Success(Unit)
    }
}