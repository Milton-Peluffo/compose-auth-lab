package com.tomildev.trakii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.navigation.NavRoute
import com.tomildev.trakii.core.navigation.NavigationRoot
import com.tomildev.trakii.ui.theme.TrakiiTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val systemTheme = isSystemInDarkTheme()
            val isDarkTheme by userPreferences.isDarkMode.collectAsState(initial = systemTheme)

            TrakiiTheme(darkTheme = true) {

                val userId by userPreferences.userId.collectAsState(initial = null)

                if (userId != null) {
                    val startRoute = if (userId != -1) {
                        NavRoute.Home
                    } else {
                        NavRoute.Settings
                        NavRoute.CompleteSignUp
                    }

                    val navController = rememberNavController()

                    NavigationRoot(navController = navController, startDestination = startRoute)
                }
            }
        }
    }
}