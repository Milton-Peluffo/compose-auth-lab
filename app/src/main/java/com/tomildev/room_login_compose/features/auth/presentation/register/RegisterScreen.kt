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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.presentation.components.PrimaryButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTitle
import com.tomildev.room_login_compose.core.presentation.components.TextError
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerViewmodel: RegisterViewmodel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
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
            PrimaryTitle(
                title = "HEY THERE!",
                subtitle = "Create your account"
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.name,
                onValueChange = { registerViewmodel.onNameChange(name = it) },
                label = "Name",
                isError = uiState.isNameError
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.phone,
                onValueChange = { registerViewmodel.onPhoneChange(phone = it) },
                label = "Phone",
                isError = uiState.isPhoneError
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { registerViewmodel.onEmailChange(email = it) },
                label = "Email",
                isError = uiState.isEmailError
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { registerViewmodel.onPasswordChange(password = it) },
                label = "Password",
                isError = uiState.isPasswordError
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.confirmPassword,
                onValueChange = { registerViewmodel.onConfirmPasswordChange(confirmPassword = it) },
                label = "Confirm password",
                isError = uiState.isPasswordConfirmError
            )
            uiState.errorMessage?.let { error ->
                TextError(text = error)
            }
            Spacer(Modifier.height(20.dp))
            PrimaryButton(
                text = "Sign up",
                onClick = { registerViewmodel.onRegisterClick() }
            )
            Spacer(Modifier.height(20.dp))
            AuthTextAction(
                text = "I already have an account",
                onClick = { onNavigateToLogin() }
            )
        }
    }
}