package com.tomildev.room_login_compose.features.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomildev.room_login_compose.features.auth.presentation.components.TextFieldPrimary
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextError
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTitle
import com.tomildev.room_login_compose.features.auth.presentation.components.ButtomPrimary

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerViewmodel: RegisterViewmodel = viewModel()
) {

    val uiState by registerViewmodel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->

        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthTitle(
                title = "HEY THERE!",
                subtitle = "Create your account"
            )
            TextFieldPrimary(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { registerViewmodel.onEmailChange(email = it) },
                label = "Email",
                isError = uiState.isEmailError
            )
            TextFieldPrimary(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { registerViewmodel.onPasswordChange(password = it) },
                label = "Password",
                isError = uiState.isPasswordError
            )
            TextFieldPrimary(
                modifier = Modifier,
                value = uiState.confirmPassword,
                onValueChange = { registerViewmodel.onConfirmPasswordChange(confirmPassword = it)},
                label = "Confirm password",
                isError = uiState.isPasswordConfirmError
            )
            uiState.errorMessage?.let { error ->
                AuthTextError(text = error)
            }
            Spacer(Modifier.height(20.dp))
            ButtomPrimary(
                text = "Sign up",
                onClick = { registerViewmodel.onValidateUserFields() }
            )
            Spacer(Modifier.height(20.dp))
            AuthTextAction(
                text = "I already have an account",
                onClick = { }
            )
        }
    }
}