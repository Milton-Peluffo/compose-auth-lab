package com.tomildev.room_login_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tomildev.room_login_compose.features.auth.presentation.login.LoginScreen
import com.tomildev.room_login_compose.features.auth.presentation.register.RegisterScreen
import com.tomildev.room_login_compose.features.home.HomeScren
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
                HomeScren()
            }
        }
    }
}