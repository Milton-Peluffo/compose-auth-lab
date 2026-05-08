package com.tomildev.trakii.core.domain.use_case.session

import com.tomildev.trakii.core.domain.repository.AuthSessionStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveReauthenticationRequiredUseCase @Inject constructor(
    private val repository: AuthSessionStateRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.observeReauthenticationRequired()
    }
}
