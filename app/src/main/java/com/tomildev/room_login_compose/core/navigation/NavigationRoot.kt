package com.tomildev.room_login_compose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tomildev.room_login_compose.features.auth.presentation.login.LoginScreen
import com.tomildev.room_login_compose.features.auth.presentation.register.RegisterScreen
import com.tomildev.room_login_compose.features.home.HomeScreen
import com.tomildev.room_login_compose.features.settings.presentation.SettingsScreen

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any = NavRoute.Login
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<NavRoute.Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(NavRoute.Register)
                },
                onNavigateToHome = { email ->
                    navController.navigate(NavRoute.Home(email))
                }
            )
        }

        composable<NavRoute.Register> {
            RegisterScreen(onNavigateToLogin = {
                navController.navigate(NavRoute.Login)
            }, onNavigateToHome = { email ->
                navController.navigate(NavRoute.Home(email))
            })
        }

        composable<NavRoute.Home> { backStackEntry ->
            val args = backStackEntry.toRoute<NavRoute.Home>()
            HomeScreen(
                email = args.email,
                onNavigateToLogin = {
                    navController.navigate(NavRoute.Login)
                },
                onNavigateToSettings = {
                    navController.navigate(NavRoute.Settings(args.email))
                }
            )
        }

        composable<NavRoute.Settings> { backStackEntry ->
            val args = backStackEntry.toRoute<NavRoute.Settings>()
            SettingsScreen(
                email = args.email,
                onNavigateToHome = {
                    navController.popBackStack()
                }
            )
        }
    }
}