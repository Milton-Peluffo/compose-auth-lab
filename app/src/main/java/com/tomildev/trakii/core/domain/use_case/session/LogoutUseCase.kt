package com.tomildev.trakii.core.domain.use_case.session

import com.tomildev.trakii.core.domain.repository.SessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() {
        sessionRepository.logout()
    }
}
