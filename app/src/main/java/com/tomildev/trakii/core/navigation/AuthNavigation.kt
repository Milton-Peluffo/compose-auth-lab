package com.tomildev.trakii.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tomildev.trakii.features.auth.forgot_password.email_request.presentation.EmailRequestScreen
import com.tomildev.trakii.features.auth.forgot_password.update_password.presentation.UpdatePasswordScreen
import com.tomildev.trakii.features.auth.otp.presentation.OtpScreen
import com.tomildev.trakii.features.auth.signin.presentation.SignInScreen
import com.tomildev.trakii.features.auth.signup.presentation.complete_signup.CompleteSignUpScreen
import com.tomildev.trakii.features.auth.signup.presentation.signup.SignUpScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    composable<NavRoute.Auth.SignIn> { backStackEntry ->
        val args = backStackEntry.toRoute<NavRoute.Auth.SignIn>()
        SignInScreen(
            showPasswordUpdatedSnackbar = args.showPasswordUpdatedSnackbar,
            onNavigateToRegister = { navController.navigate(NavRoute.Auth.SignUp) },
            onNavigateToHabitList = { navController.navigate(NavRoute.HabitList) },
            onNavigateToForgotPassword = { navController.navigate(NavRoute.Auth.ForgotPasswordEmailRequest) }
        )
    }

    composable<NavRoute.Auth.SignUp> {
        SignUpScreen(
            onNavigateToLogin = { navController.navigate(NavRoute.Auth.SignIn()) },
            onNavigateToOtp = { email -> navController.navigate(NavRoute.Auth.Otp(email, false)) },
            onNavigateToHabitList = { navController.navigate(NavRoute.HabitList) }
        )
    }

    composable<NavRoute.Auth.Otp> { backStackEntry ->
        val args = backStackEntry.toRoute<NavRoute.Auth.Otp>()
        OtpScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToHabitList = {
                navController.navigate(NavRoute.HabitList) {
                    popUpTo(NavRoute.Auth.SignUp) { inclusive = true }
                }
            },
            onNavigateToCompleteSignUp = { email ->
                navController.navigate(NavRoute.Auth.CompleteSignUp(email)) {
                    popUpTo(NavRoute.Auth.SignUp) { inclusive = true }
                }
            },
            onNavigateToUpdatePassword = {
                navController.navigate(NavRoute.Auth.ForgotPasswordReset) {
                    popUpTo(NavRoute.Auth.ForgotPasswordEmailRequest) { inclusive = true }
                }
            }
        )
    }

    composable<NavRoute.Auth.CompleteSignUp> {
        CompleteSignUpScreen(
            onNavigateToHabitList = {
                navController.navigate(NavRoute.HabitList) {
                    popUpTo(NavRoute.Auth.SignUp) { inclusive = true }
                }
            }
        )
    }

    composable<NavRoute.Auth.ForgotPasswordReset> {
        UpdatePasswordScreen(
            onNavigateToSignIn = { showSnackbar ->
                navController.navigate(NavRoute.Auth.SignIn(showSnackbar)) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }

    composable<NavRoute.Auth.ForgotPasswordEmailRequest> {
        EmailRequestScreen(
            onNavigateToSignIn = { navController.popBackStack() },
            onNavigateToOtp = { email -> navController.navigate(NavRoute.Auth.Otp(email, true)) }
        )
    }
}