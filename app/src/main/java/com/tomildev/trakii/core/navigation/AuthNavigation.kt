package com.tomildev.trakii.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tomildev.trakii.features.auth.signin.presentation.SignInScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    composable<NavRoute.Auth.SignIn> {
        SignInScreen(
            onNavigateToHabitList = {
                navController.navigate(NavRoute.HabitList) {
                    popUpTo(NavRoute.Auth.SignIn) { inclusive = true }
                }
            },
            onNavigateToOnBoarding = {
                navController.navigate(NavRoute.OnBoarding) {
                    popUpTo(NavRoute.Auth.SignIn) { inclusive = true }
                }
            }
        )
    }
}
