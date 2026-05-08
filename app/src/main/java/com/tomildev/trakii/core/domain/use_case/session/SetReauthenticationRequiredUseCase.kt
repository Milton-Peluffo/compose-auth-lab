package com.tomildev.trakii.core.domain.use_case.session

import com.tomildev.trakii.core.domain.repository.AuthSessionStateRepository
import javax.inject.Inject

class SetReauthenticationRequiredUseCase @Inject constructor(
    private val repository: AuthSessionStateRepository
) {
    suspend operator fun invoke(required: Boolean) {
        repository.setReauthenticationRequired(required)
    }
}
