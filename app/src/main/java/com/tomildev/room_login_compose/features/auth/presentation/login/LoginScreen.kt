package com.tomildev.room_login_compose.features.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.presentation.components.OutlinedPrimaryButton
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction
import com.tomildev.room_login_compose.core.presentation.components.PrimaryButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTitle
import com.tomildev.room_login_compose.core.presentation.components.TextError

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: (String) -> Unit
) {

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            onNavigateToHome(uiState.email)
        }
    }

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
                value = uiState.email,
                onValueChange = { loginViewModel.onEmailChange(email = it) },
                label = "Email"
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { loginViewModel.onPasswordChange(password = it) },
                label = "Password"
            )
            uiState.errorMessage?.let { error ->
                TextError(text = error)
            }
            Spacer(Modifier.height(20.dp))
            PrimaryButton(
                text = "Login",
                isLoading = uiState.isLoading,
                onClick = { loginViewModel.onLoginClick() }
            )
            Spacer(Modifier.height(15.dp))
            OutlinedPrimaryButton(
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