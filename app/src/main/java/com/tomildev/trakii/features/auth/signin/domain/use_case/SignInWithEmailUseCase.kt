package com.tomildev.trakii.features.auth.signin.domain.use_case

import com.tomildev.trakii.features.auth.signin.domain.SignInRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val repository: SignInRepository
) {
    suspend fun execute(email: String, password: String) =
        repository.signInWithEmail(email, password)
}