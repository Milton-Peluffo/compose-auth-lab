package com.tomildev.trakii.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tomildev.trakii.features.auth.forgot_password.email_request.presentation.EmailRequestScreen
import com.tomildev.trakii.features.auth.forgot_password.update_password.presentation.UpdatePasswordScreen
import com.tomildev.trakii.features.auth.otp.presentation.OtpScreen
import com.tomildev.trakii.features.auth.signin.presentation.SignInScreen
import com.tomildev.trakii.features.auth.signup.presentation.complete_signup.CompleteSignUpScreen
import com.tomildev.trakii.features.auth.signup.presentation.signup.SignUpScreen
import com.tomildev.trakii.features.habit.habit_list.presentation.HabitListScreen
import com.tomildev.trakii.features.settings.presentation.SettingsScreen

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any = NavRoute.SignIn()
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<NavRoute.SignIn> { backStackEntry ->
            val args = backStackEntry.toRoute<NavRoute.SignIn>()
            SignInScreen(
                showPasswordUpdatedSnackbar = args.showPasswordUpdatedSnackbar,
                onNavigateToRegister = {
                    navController.navigate(NavRoute.SignUp)
                },
                onNavigateToHabitList = {
                    navController.navigate(NavRoute.HabitList)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(NavRoute.ForgotPasswordEmailRequest)
                }
            )
        }

        composable<NavRoute.SignUp> {
            SignUpScreen(onNavigateToLogin = {
                navController.navigate(NavRoute.SignIn())
            }, onNavigateToOtp = { email ->
                navController.navigate(NavRoute.Otp(email = email, isRecovery = false))
            }, onNavigateToHabitList = {
                navController.navigate(NavRoute.HabitList)
            }
            )
        }

        composable<NavRoute.Otp> { backStackEntry ->
            val args = backStackEntry.toRoute<NavRoute.Otp>()
            OtpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHabitList = {
                    navController.navigate(NavRoute.HabitList) {
                        popUpTo(NavRoute.SignUp) { inclusive = true }
                    }
                },
                onNavigateToCompleteSignUp = { email ->
                    navController.navigate(NavRoute.CompleteSignUp(email = email)) {
                        popUpTo(NavRoute.SignUp) { inclusive = true }
                    }
                },
                onNavigateToUpdatePassword = {
                    navController.navigate(NavRoute.ForgotPasswordReset) {
                        popUpTo(NavRoute.ForgotPasswordEmailRequest) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.CompleteSignUp> {
            CompleteSignUpScreen(
                onNavigateToHabitList = {
                    navController.navigate(NavRoute.HabitList) {
                        popUpTo(NavRoute.SignUp) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.ForgotPasswordReset> {
            UpdatePasswordScreen(
                onNavigateToSignIn = { showSnackbar ->
                    navController.navigate(NavRoute.SignIn(showPasswordUpdatedSnackbar = showSnackbar)) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.ForgotPasswordEmailRequest> {
            EmailRequestScreen(onNavigateToSignIn = {
                navController.popBackStack()
            }) { email ->
                navController.navigate(NavRoute.Otp(email = email, isRecovery = true))
            }
        }

        composable<NavRoute.HabitList> {
            HabitListScreen(
                onNavigateToSettings = {
                    navController.navigate(NavRoute.Settings)
                }
            )
        }

        composable<NavRoute.Settings> {
            SettingsScreen(
                onNavigateToHabitList = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(NavRoute.SignIn()) {
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