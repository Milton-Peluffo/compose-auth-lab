package com.tomildev.trakii.core.domain.use_case.session

import com.tomildev.trakii.core.domain.repository.AuthSessionStateRepository
import javax.inject.Inject

class SetPasswordRecoveryInProgressUseCase @Inject constructor(
    private val repository: AuthSessionStateRepository
) {
    suspend operator fun invoke(inProgress: Boolean) {
        repository.setPasswordRecoveryInProgress(inProgress)
    }
}
