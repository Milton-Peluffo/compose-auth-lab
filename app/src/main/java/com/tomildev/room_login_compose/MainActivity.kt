package com.tomildev.room_login_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.tomildev.room_login_compose.core.data.session.SessionManager
import com.tomildev.room_login_compose.core.navigation.NavRoute
import com.tomildev.room_login_compose.core.navigation.NavigationRoot
import com.tomildev.room_login_compose.core.presentation.debug.SandboxScreen
import com.tomildev.room_login_compose.ui.theme.Room_login_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
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

                val userId by sessionManager.userId.collectAsState(initial = null)

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