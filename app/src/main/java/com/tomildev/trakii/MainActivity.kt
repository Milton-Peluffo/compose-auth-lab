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
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
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

    @Inject
    lateinit var sessionRepository: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val systemTheme = isSystemInDarkTheme()
            val isDarkTheme by userPreferences.isDarkMode.collectAsState(initial = systemTheme)
            val isReauthenticationRequired by userPreferences.isReauthenticationRequired
                .collectAsState(initial = false)

            val sessionState by sessionRepository.observeSession()
                .collectAsState(initial = SessionState.Loading)

            TrakiiTheme(darkTheme = true) {
                if (sessionState !is SessionState.Loading) {
                    val startRoute: Any = when (sessionState) {
                        is SessionState.Authenticated -> {
                            if (isReauthenticationRequired) {
                                NavRoute.Auth.SignIn()
                            } else {
                                NavRoute.HabitList
                            }
                        }
                        else -> NavRoute.Auth.SignIn()
                    }

                    val navController = rememberNavController()
                    NavigationRoot(navController = navController, startDestination = startRoute)
                }
            }
        }
    }
}
