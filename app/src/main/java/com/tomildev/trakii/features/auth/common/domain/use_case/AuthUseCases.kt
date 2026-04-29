package com.tomildev.trakii.features.auth.common.domain.use_case

import com.tomildev.trakii.features.auth.otp.domain.use_case.ResendOtpUseCase
import com.tomildev.trakii.features.auth.otp.domain.use_case.VerifyOtpUseCase
import com.tomildev.trakii.features.auth.signin.domain.use_case.SignInWithEmailUseCase
import com.tomildev.trakii.features.auth.signup.domain.use_case.SendOtpUseCase
import javax.inject.Inject

/**
 * Wrapper for all authentication use cases.
 * Simplifies dependency injection in ViewModels.
 */
data class AuthUseCases @Inject constructor(
    val sendOtp: SendOtpUseCase,
    val verifyOtp: VerifyOtpUseCase,
    val resendOtp: ResendOtpUseCase,
    val authWithGoogle: AuthWithGoogleUseCase,
    val signInWithEmail: SignInWithEmailUseCase
)