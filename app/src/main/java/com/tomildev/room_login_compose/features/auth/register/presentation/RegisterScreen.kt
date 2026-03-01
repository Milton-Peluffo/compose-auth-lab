package com.tomildev.room_login_compose.features.auth.register.presentation

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.common.presentation.asString
import com.tomildev.room_login_compose.core.common.presentation.components.PrimaryButton
import com.tomildev.room_login_compose.core.common.presentation.components.PrimarySubtitle
import com.tomildev.room_login_compose.core.common.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.common.presentation.components.PrimaryTitle
import com.tomildev.room_login_compose.core.common.presentation.components.TextError
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerViewmodel: RegisterViewmodel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: (String) -> Unit
) {

    val uiState by registerViewmodel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isRegistrationSuccess) {
        if (uiState.isRegistrationSuccess) {
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
                title = "HEY THERE!",
                subtitle = "Create your account"
            )
            PrimarySubtitle(text = "Fill fields below to get started")
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.name,
                onValueChange = { registerViewmodel.onNameChange(name = it) },
                label = "Name",
                isError = uiState.isNameError
            )
            if (uiState.isNameError){
                TextError(text = uiState.nameError!!.asString())
            }
            Spacer(Modifier.height(5.dp))
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { registerViewmodel.onEmailChange(email = it) },
                label = "Email",
                isError = uiState.isEmailError
            )
            if (uiState.isEmailError){
                TextError(text = uiState.emailError!!.asString())
            }
            Spacer(Modifier.height(5.dp))
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { registerViewmodel.onPasswordChange(password = it) },
                label = "Password",
                isError = uiState.isPasswordError,
                isPasswordField = true
            )
            if (uiState.isPasswordError){
                TextError(text = uiState.passwordError!!.asString())
            }
            Spacer(Modifier.height(5.dp))
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.confirmPassword,
                onValueChange = { registerViewmodel.onConfirmPasswordChange(confirmPassword = it) },
                label = "Confirm password",
                isError = uiState.isPasswordConfirmError,
                isPasswordField = true
            )
            if (uiState.isPasswordConfirmError){
                TextError(text = uiState.passwordConfirmError!!.asString())
            }
            Spacer(Modifier.height(25.dp))
            PrimaryButton(
                text = "Sign Up",
                isLoading = uiState.isLoading,
                onClick = { registerViewmodel.onRegisterClick() }
            )
            Spacer(Modifier.height(20.dp))
            AuthTextAction(
                text = "Already Have Account? Log In",
                onClick = { onNavigateToLogin() },
                textAlign = TextAlign.Center
            )
        }
    }
}