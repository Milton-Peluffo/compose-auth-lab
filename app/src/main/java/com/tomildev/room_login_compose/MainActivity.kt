package com.tomildev.room_login_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.tomildev.room_login_compose.core.data.preferences.UserPreferences
import com.tomildev.room_login_compose.core.navigation.NavRoute
import com.tomildev.room_login_compose.core.navigation.NavigationRoot
import com.tomildev.room_login_compose.ui.theme.Room_login_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val isDarkTheme by userPreferences.isDarkMode.collectAsState(initial = false)

            Room_login_composeTheme(darkTheme = isDarkTheme) {

                val userId by userPreferences.userId.collectAsState(initial = null)

                if (userId != null) {
                    val startRoute = if (userId != -1) {
                        NavRoute.Home
                    } else {
                        NavRoute.Login
                    }

                    val navController = rememberNavController()

                    NavigationRoot(navController = navController, startDestination = startRoute)

//                SandboxScreen()
                }
            }
        }
    }
}