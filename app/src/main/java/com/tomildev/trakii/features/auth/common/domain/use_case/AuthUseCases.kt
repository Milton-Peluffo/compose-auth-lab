package com.tomildev.trakii.features.auth.common.domain.use_case

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val authWithGoogle: AuthWithGoogleUseCase
)
