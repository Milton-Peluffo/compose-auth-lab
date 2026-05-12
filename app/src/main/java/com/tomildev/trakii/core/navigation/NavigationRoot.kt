package com.tomildev.trakii.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tomildev.trakii.features.habit.habit_list.presentation.HabitListScreen
import com.tomildev.trakii.features.onboarding.presentation.OnBoardingScreen

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any = NavRoute.Auth.SignIn
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<NavRoute.OnBoarding> {
            OnBoardingScreen(
                onNavigateToHabitList = {
                    navController.navigate(NavRoute.HabitList) {
                        popUpTo(NavRoute.OnBoarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        authGraph(navController)

        composable<NavRoute.HabitList> {
            HabitListScreen(
                onNavigateToSettings = {
                    navController.navigate(NavRoute.Settings.Main)
                }
            )
        }

        settingsGraph(navController)
    }
}
