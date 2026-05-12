package com.tomildev.trakii.features.settings.subsettings.account.domain.use_case

import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.repository.SessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke() {
        userPreferences.logOut()
        sessionRepository.logOut()
    }
}