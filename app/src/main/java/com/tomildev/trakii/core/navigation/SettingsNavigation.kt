package com.tomildev.trakii.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tomildev.trakii.features.settings.main_settings.presentation.SettingsScreen
import com.tomildev.trakii.features.settings.main_settings.presentation.SettingsUiEvent
import com.tomildev.trakii.features.settings.sub_settings.account.presentation.AccountSettingsScreen
import com.tomildev.trakii.features.settings.sub_settings.appareance.presentation.AppearanceScreen
import com.tomildev.trakii.features.settings.sub_settings.appareance.presentation.AppearanceUiEvent

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    navigation<NavRoute.SettingsGraph>(startDestination = NavRoute.Settings.Main) {

        composable<NavRoute.Settings.Main> {
            SettingsScreen(
                onNavigationEvent = { event ->
                    when (event) {
                        SettingsUiEvent.NavigateToHabitList -> navController.popBackStack()
                        SettingsUiEvent.NavigateToAccount -> navController.navigate(NavRoute.Settings.Account)
                        SettingsUiEvent.NavigateToAppearance -> navController.navigate(NavRoute.Settings.Appearance)
                        SettingsUiEvent.NavigateToNotifications -> navController.navigate(NavRoute.Settings.Notifications)
                        SettingsUiEvent.NavigateToLanguage -> navController.navigate(NavRoute.Settings.Language)
                        SettingsUiEvent.NavigateToDatacontrols -> navController.navigate(NavRoute.Settings.DataControl)
                        SettingsUiEvent.NavigateToAboutApp -> navController.navigate(NavRoute.Settings.AboutApp)
                        SettingsUiEvent.NavigateToSignIn -> navController.navigate(NavRoute.Auth.SignIn) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable<NavRoute.Settings.Account> {
            AccountSettingsScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable<NavRoute.Settings.Appearance> {
            AppearanceScreen(
                onNavigationEvent = { event ->
                    when (event) {
                        AppearanceUiEvent.NavigateToMainSettings -> navController.popBackStack()
                        else -> {}
                    }
                }
            )
        }
    }
}