package com.tomildev.room_login_compose.features.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction
import com.tomildev.room_login_compose.core.presentation.components.PrimaryButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryIconButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTitle

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit
) {
    Scaffold { innerPadding ->

        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryTitle(
                title = "WELCOME BACK!",
                subtitle = "Log in to continue"
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { "" },
                label = "Email"
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { "" },
                label = "Password"
            )
            Spacer(Modifier.height(20.dp))
            PrimaryButton(
                text = "Login",
                onClick = {}
            )
            Spacer(Modifier.height(15.dp))
            PrimaryIconButton(
                text = "Create an account",
                onClick = { onNavigateToRegister() })
            Spacer(Modifier.height(20.dp))
            AuthTextAction(
                text = "I forgot my password",
                onClick = { }
            )
        }
    }
}