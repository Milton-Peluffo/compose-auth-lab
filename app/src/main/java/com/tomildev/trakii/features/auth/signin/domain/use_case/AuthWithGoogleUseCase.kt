package com.tomildev.trakii.features.auth.signin.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signin.domain.OAuthRepository
import javax.inject.Inject

class AuthWithGoogleUseCase @Inject constructor(
    private val repository: OAuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<Unit, DataError.Network> {
        return repository.authWithGoogle(idToken)
    }
}