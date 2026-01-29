package com.tomildev.room_login_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.tomildev.room_login_compose.core.data.session.SessionManager
import com.tomildev.room_login_compose.core.navigation.NavRoute
import com.tomildev.room_login_compose.core.navigation.NavigationRoot
import com.tomildev.room_login_compose.features.auth.presentation.login.LoginScreen
import com.tomildev.room_login_compose.features.home.HomeScreen
import com.tomildev.room_login_compose.features.settings.presentation.SettingsScreen
import com.tomildev.room_login_compose.ui.theme.Room_login_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Room_login_composeTheme {

//                val userEmail = sessionManager.getSession()
//
//                val startRoute = if (userEmail != null) {
//                    NavRoute.Home(email = userEmail)
//                } else {
//                    NavRoute.Login
//                }
//
//                val navController = rememberNavController()
//
//                NavigationRoot(navController = navController, startDestination = startRoute)

                SettingsScreen()
            }
        }
    }
}