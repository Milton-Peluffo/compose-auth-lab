package com.tomildev.room_login_compose.features.auth.signup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.room_login_compose.core.common.presentation.components.PrimarySubtitle
import com.tomildev.room_login_compose.core.common.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.common.presentation.components.PrimaryTitle
import com.tomildev.room_login_compose.core.common.presentation.components.TextError
import com.tomildev.room_login_compose.core.common.presentation.components.snackbars.SnackBars
import com.tomildev.room_login_compose.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.room_login_compose.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.room_login_compose.core.common.presentation.mapper.toUiText
import com.tomildev.room_login_compose.features.common.presentation.components.AuthTextAction
import com.tomildev.room_login_compose.features.common.presentation.components.RegistrationSuccessDialog

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    registerViewmodel: RegisterViewmodel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToOtp: (String) -> Unit
) {

    val uiState by registerViewmodel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        registerViewmodel.uiEvents.collect { uiEvent ->
            when (uiEvent) {
                is SignUpUiEvent.NavigateToOtp -> {
                    onNavigateToOtp(uiEvent.email)
                }

                is SignUpUiEvent.Error, is SignUpUiEvent.Warning -> {
                    val (errorData, snackbarType) = when (uiEvent) {
                        is SignUpUiEvent.Error -> uiEvent.error to SnackbarType.Error
                        is SignUpUiEvent.Warning -> uiEvent.error to SnackbarType.Warning
                        else -> return@collect
                    }

                    val errorMessage = errorData.toUiText().asString(context)
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorMessage,
                            type = snackbarType
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(vertical = 20.dp),
                hostState = snackbarHostState
            ) { data ->
                val customVisuals = data.visuals as? SnackbarVisualsCustom

                if (customVisuals != null) {
                    when (customVisuals.type) {
                        SnackbarType.Error -> SnackBars.Error(
                            title = customVisuals.message,
                            description = customVisuals.description,
                            onClick = { data.dismiss() }
                        )

                        SnackbarType.Success -> SnackBars.Success(
                            title = customVisuals.message,
                            description = customVisuals.description,
                            onClick = { data.dismiss() }
                        )

                        SnackbarType.Warning -> SnackBars.Warning(
                            title = customVisuals.message,
                            description = customVisuals.description,
                            onClick = { data.dismiss() }
                        )

                        else -> {}
                    }
                } else {
                    Snackbar(snackbarData = data)
                }
            }
        }
    ) { innerPadding ->

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
                isError = uiState.nameError != null
            )
            if (uiState.nameError != null) {
                TextError(text = uiState.nameError!!.toUiText().asString())
            }
            Spacer(Modifier.height(5.dp))
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { registerViewmodel.onEmailChange(email = it) },
                label = "Email",
                isError = uiState.emailError != null
            )
            if (uiState.emailError != null) {
                TextError(text = uiState.emailError!!.toUiText().asString())
            }
            Spacer(Modifier.height(5.dp))
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { registerViewmodel.onPasswordChange(password = it) },
                label = "Password",
                isError = uiState.passwordError != null,
                isPasswordField = true
            )
            if (uiState.passwordError != null) {
                TextError(text = uiState.passwordError!!.toUiText().asString())
            }
            Spacer(Modifier.height(5.dp))
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.confirmPassword,
                onValueChange = { registerViewmodel.onConfirmPasswordChange(confirmPassword = it) },
                label = "Confirm password",
                isError = uiState.passwordConfirmError != null,
                isPasswordField = true
            )
            if (uiState.passwordConfirmError != null) {
                TextError(text = uiState.passwordConfirmError!!.toUiText().asString())
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
            Spacer(Modifier.padding(vertical = 20.dp))
            if (uiState.showSuccessDialog) {
                RegistrationSuccessDialog(
                    onConfirm = {
                        onNavigateToLogin()
                        registerViewmodel.onDismissDialog()
                    }
                )
            }
        }
    }
}