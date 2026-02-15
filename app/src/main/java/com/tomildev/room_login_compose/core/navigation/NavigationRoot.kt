package com.tomildev.room_login_compose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tomildev.room_login_compose.features.auth.presentation.login.LoginScreen
import com.tomildev.room_login_compose.features.auth.presentation.register.RegisterScreen
import com.tomildev.room_login_compose.features.home.HomeScreen
import com.tomildev.room_login_compose.features.settings.presentation.SettingsScreen
import com.tomildev.room_login_compose.features.settings.profile_info.presentation.ProfileInfoScreen

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
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home)
                }
            )
        }

        composable<NavRoute.Register> {
            RegisterScreen(onNavigateToLogin = {
                navController.navigate(NavRoute.Login)
            }, onNavigateToHome = {
                navController.navigate(NavRoute.Home)
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
                },
                onNavigateToAccountInfo = {
                    navController.navigate(NavRoute.AccountInfo)
                },
                onNavigateToAccountPassword = {
                    navController.navigate(NavRoute.AccountPassword)
                }
            )
        }

        composable<NavRoute.AccountInfo> {
            ProfileInfoScreen()
        }

        composable<NavRoute.AccountPassword> {

        }
    }
}