package com.tomildev.trakii.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {

    @Serializable
    data object SignIn : NavRoute

    @Serializable
    data object SignUp : NavRoute

    @Serializable
    data class Otp(val email: String, val isRecovery: Boolean = false) : NavRoute

    @Serializable
    data class CompleteSignUp(val email: String) : NavRoute

    @Serializable
    data object ForgotPasswordReset : NavRoute

    @Serializable
    data object ForgotPasswordEmailRequest : NavRoute
    @Serializable
    data object Home : NavRoute

    @Serializable
    data object Settings : NavRoute
}