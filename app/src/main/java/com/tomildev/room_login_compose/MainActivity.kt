package com.tomildev.room_login_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.tomildev.room_login_compose.core.navigation.NavigationRoot
import com.tomildev.room_login_compose.features.home.HomeScreen
import com.tomildev.room_login_compose.ui.theme.Room_login_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Room_login_composeTheme {
//                LoginScreen()
//                RegisterScreen()

                val navController = rememberNavController()

                NavigationRoot(navController = navController)

                // HomeScreen()
            }
        }
    }
}