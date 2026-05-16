package com.tomildev.trakii.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tomildev.trakii.features.settings.main_settings.presentation.MainSettingsScreen
import com.tomildev.trakii.features.settings.main_settings.presentation.MainSettingsUiEvent
import com.tomildev.trakii.features.settings.sub_settings.about.presentation.AboutAppScreen
import com.tomildev.trakii.features.settings.sub_settings.account.presentation.AccountSettingsScreen
import com.tomildev.trakii.features.settings.sub_settings.appareance.presentation.AppearanceScreen
import com.tomildev.trakii.features.settings.sub_settings.data_management.presentation.DataManagementScreen
import com.tomildev.trakii.features.settings.sub_settings.language.presentation.LanguageScreen
import com.tomildev.trakii.features.settings.sub_settings.notifications.presentation.NotificationsScreen

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    navigation<NavRoute.SettingsGraph>(startDestination = NavRoute.Settings.Main) {

        composable<NavRoute.Settings.Main> {
            MainSettingsScreen(
                onNavigationEvent = { event ->
                    when (event) {
                        MainSettingsUiEvent.NavigateToHabitList -> navController.popBackStack()
                        MainSettingsUiEvent.NavigateToAccount -> navController.navigate(NavRoute.Settings.Account)
                        MainSettingsUiEvent.NavigateToAppearance -> navController.navigate(NavRoute.Settings.Appearance)
                        MainSettingsUiEvent.NavigateToNotifications -> navController.navigate(
                            NavRoute.Settings.Notifications
                        )

                        MainSettingsUiEvent.NavigateToLanguage -> navController.navigate(NavRoute.Settings.Language)
                        MainSettingsUiEvent.NavigateToDataManagement -> navController.navigate(
                            NavRoute.Settings.DataManagement
                        )

                        MainSettingsUiEvent.NavigateToAboutApp -> navController.navigate(NavRoute.Settings.AboutApp)
                        MainSettingsUiEvent.NavigateToSignIn -> navController.navigate(NavRoute.Auth.SignIn) {
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
                onNavigateToMainSettings = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Settings.Notifications> {
            NotificationsScreen(
                onNavigateToMainSettings = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Settings.Language> {
            LanguageScreen(
                onNavigateToMainSettings = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Settings.DataManagement> {
            DataManagementScreen(
                onNavigateToMainSettings = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Settings.AboutApp> {
            AboutAppScreen(
                onNavigateToMainSettings = { navController.popBackStack() }
            )
        }
    }
}
