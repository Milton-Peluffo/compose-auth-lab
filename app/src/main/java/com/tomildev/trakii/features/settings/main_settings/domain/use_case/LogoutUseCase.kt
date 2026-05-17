package com.tomildev.trakii.features.settings.main_settings.domain.use_case

import com.tomildev.trakii.core.domain.repository.SessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() {
        try {
            sessionRepository.logOut()
        } catch (_: Exception) {
        }
    }
}
