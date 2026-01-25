package com.tomildev.room_login_compose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tomildev.room_login_compose.features.auth.presentation.login.LoginScreen
import com.tomildev.room_login_compose.features.auth.presentation.register.RegisterScreen
import com.tomildev.room_login_compose.features.home.HomeScreen

@Composable
fun NavigationRoot(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login
    ) {

        composable<NavRoute.Login> {
            LoginScreen(onNavigateToRegister = {
                navController.navigate(NavRoute.Register)
            })
        }

        composable<NavRoute.Register> {
            RegisterScreen(onNavigateToLogin = {
                navController.navigate(NavRoute.Login)
            }
            )
        }

        composable<NavRoute.Home> { backStackEntry ->
            val args = backStackEntry.toRoute<NavRoute.Home>()
            HomeScreen(email = args.email)
        }
    }
}