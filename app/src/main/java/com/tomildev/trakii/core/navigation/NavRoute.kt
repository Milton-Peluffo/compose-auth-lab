package com.tomildev.trakii.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {

    @Serializable
    enum class OtpPurpose {
        Auth,
        Recovery,
        AccountPasswordUpdate
    }

    @Serializable
    enum class PasswordUpdateOrigin {
        PasswordRecovery,
        AccountSettings
    }

    sealed interface Auth : NavRoute {
        @Serializable
        data class SignIn(
            val showPasswordUpdatedSnackbar: Boolean = false
        ) : Auth
        @Serializable data object SignUp : Auth
        @Serializable
        data class Otp(
            val email: String,
            val isRecovery: Boolean = false,
            val purpose: OtpPurpose = OtpPurpose.Auth
        ) : Auth
        @Serializable data class CompleteSignUp(val email: String) : Auth
        @Serializable data object ForgotPasswordEmailRequest : Auth
        @Serializable
        data class ForgotPasswordReset(
            val origin: PasswordUpdateOrigin = PasswordUpdateOrigin.PasswordRecovery
        ) : Auth
    }
    @Serializable data object HabitList : NavRoute

    @Serializable data object SettingsGraph : NavRoute
    @Serializable
    sealed interface Settings : NavRoute {
        @Serializable data object Main : Settings
        @Serializable data object Account : Settings
        @Serializable data object Notifications : Settings
        @Serializable data object Appearance : Settings
        @Serializable data object Language : Settings
        @Serializable data object DataControl : Settings
        @Serializable data object AboutApp : Settings
    }
}
