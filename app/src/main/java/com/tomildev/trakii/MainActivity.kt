package com.tomildev.trakii

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.tomildev.trakii.core.data.preferences.AppPreferences
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.SessionState
import com.tomildev.trakii.core.navigation.NavRoute
import com.tomildev.trakii.core.navigation.NavigationRoot
import com.tomildev.trakii.ui.theme.TrakiiTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var sessionRepository: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appearance by appPreferences.appearance.collectAsState(initial = "system")

            val useDarkTheme = when (appearance) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            val sessionState by sessionRepository.observeSession()
                .collectAsState(initial = SessionState.Loading)

            TrakiiTheme(darkTheme = useDarkTheme) {
                if (sessionState !is SessionState.Loading) {
                    val startRoute = when (val state = sessionState) {
                        is SessionState.Authenticated -> {
                            if (state.user.onBoardingCompleted) NavRoute.HabitList
                            else NavRoute.OnBoarding
                        }

                        else -> NavRoute.Auth.SignIn
                    }

                    val navController = rememberNavController()
                    NavigationRoot(
                        navController = navController,
                        startDestination = startRoute
                    )
                }
            }
        }
    }
}
