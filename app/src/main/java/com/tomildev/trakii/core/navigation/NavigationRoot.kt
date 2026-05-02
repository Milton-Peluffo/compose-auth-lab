package com.tomildev.trakii.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tomildev.trakii.features.auth.forgot_password.email_request.presentation.EmailRequestScreen
import com.tomildev.trakii.features.auth.forgot_password.update_password.presentation.Auth_UpdatePasswordScreen
import com.tomildev.trakii.features.auth.otp.presentation.OtpScreen
import com.tomildev.trakii.features.auth.signin.presentation.SignInScreen
import com.tomildev.trakii.features.auth.signup.presentation.complete_signup.CompleteSignUpScreen
import com.tomildev.trakii.features.auth.signup.presentation.signup.SignUpScreen
import com.tomildev.trakii.features.home.HomeScreen
import com.tomildev.trakii.features.settings.presentation.SettingsScreen

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any = NavRoute.SignIn
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<NavRoute.SignIn> {
            SignInScreen(
                onNavigateToRegister = {
                    navController.navigate(NavRoute.SignUp)
                },
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(NavRoute.ForgotPasswordEmailRequest)
                }
            )
        }

        composable<NavRoute.SignUp> {
            SignUpScreen(onNavigateToLogin = {
                navController.navigate(NavRoute.SignIn)
            }, onNavigateToOtp = { email ->
                navController.navigate(NavRoute.Otp(email = email))
            }, onNavigateToHome = {
                navController.navigate(NavRoute.Home)
            }
            )
        }

        composable<NavRoute.Otp> {
            OtpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.SignUp) { inclusive = true }
                    }
                },
                onNavigateToCompleteSignUp = { email ->
                    navController.navigate(NavRoute.CompleteSignUp(email = email)) {
                        popUpTo(NavRoute.SignUp) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.CompleteSignUp> {
            CompleteSignUpScreen(
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.SignUp) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.ForgotPasswordReset> {
            Auth_UpdatePasswordScreen(onNavigateToSignIn = {
                navController.navigate(NavRoute.SignIn)
            })
        }

        composable<NavRoute.ForgotPasswordEmailRequest> {
            EmailRequestScreen(onNavigateToSignIn = {
                navController.popBackStack()
            }) { email ->
                navController.navigate(NavRoute.Otp(email = email))
            }
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
                    navController.navigate(NavRoute.SignIn) {
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