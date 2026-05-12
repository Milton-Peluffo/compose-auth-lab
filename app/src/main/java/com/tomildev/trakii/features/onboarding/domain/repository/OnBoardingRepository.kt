package com.tomildev.trakii.features.onboarding.domain.repository

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result

interface OnboardingRepository {
    suspend fun completeOnboarding(): Result<Unit, DataError.Network>
}