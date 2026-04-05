package com.tomildev.room_login_compose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tomildev.room_login_compose.features.auth.login.presentation.LoginScreen
import com.tomildev.room_login_compose.features.auth.otp.presentation.OtpScreen
import com.tomildev.room_login_compose.features.auth.signup.presentation.SignUpScreen
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
                    navController.navigate(NavRoute.SignUp)
                },
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home)
                }
            )
        }

        composable<NavRoute.SignUp> {
            SignUpScreen(onNavigateToLogin = {
                navController.navigate(NavRoute.Login)
            }, onNavigateToOtp = { email ->
                navController.navigate(NavRoute.Otp(email = email))
            })
        }

        composable<NavRoute.Otp> {
            OtpScreen(onNavigateBack = {
                navController.popBackStack()
            }, onNavigateToHome = {
                navController.navigate(NavRoute.Home) {
                    popUpTo(NavRoute.SignUp) { inclusive = true }
                }
            })
        }

        composable<NavRoute.Home> {
            HomeScreen(
                onNavigateToSettings = {
                    navController.navigate(NavRoute.Settings)
                }
            )
        }

        composable<NavRoute.Settings> {
            SettingsScreen(
                onNavigateToHome = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(NavRoute.Login) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}