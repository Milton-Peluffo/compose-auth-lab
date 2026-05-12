package com.tomildev.trakii.features.onboarding.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.onboarding.data.repository.OnBoardingRepositoryImpl
import com.tomildev.trakii.features.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke(): Result<Unit, DataError.Network> {
        return onboardingRepository.completeOnboarding()
    }
}