package com.tomildev.room_login_compose.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {

    @Serializable
    data object Login : NavRoute

    @Serializable
    data object Register : NavRoute

    @Serializable
    data object Home : NavRoute

    @Serializable
    data object Settings : NavRoute

    @Serializable
    data object AccountInfo : NavRoute

    @Serializable
    data object AccountPassword : NavRoute
}