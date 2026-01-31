package com.tomildev.room_login_compose.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {

    @Serializable
    data object Login : NavRoute

    @Serializable
    data object Register : NavRoute

    @Serializable
    data class Home(
        val email: String
    ) : NavRoute

    @Serializable
    data class Settings(
        val email: String
    ) : NavRoute
}