package com.tomildev.trakii.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {
    @Serializable data object OnBoarding : NavRoute

    sealed interface Auth : NavRoute {
        @Serializable data object SignIn : Auth
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
        @Serializable data object DataManagement : Settings
        @Serializable data object AboutApp : Settings
    }
}