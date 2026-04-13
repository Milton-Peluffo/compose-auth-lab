package com.tomildev.room_login_compose.features.auth.signup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.room_login_compose.core.common.presentation.components.snackbars.SnackBars
import com.tomildev.room_login_compose.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.room_login_compose.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.room_login_compose.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.room_login_compose.core.common.presentation.components.textfields.TextFields
import com.tomildev.room_login_compose.core.common.presentation.components.texts.TextError
import com.tomildev.room_login_compose.core.common.presentation.components.texts.Texts
import com.tomildev.room_login_compose.core.common.presentation.mapper.toUiText
import com.tomildev.room_login_compose.features.auth.common.components.AuthHorizontalDivider
import com.tomildev.room_login_compose.features.auth.common.components.AuthTextAction
import com.tomildev.room_login_compose.features.auth.common.components.social.SocialAuthButtons
import com.tomildev.room_login_compose.ui.theme.Dimens

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpViewmodel: SignUpViewmodel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToOtp: (String) -> Unit
) {

    val uiState by signUpViewmodel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        signUpViewmodel.uiEvents.collect { uiEvent ->
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
                modifier = Modifier.padding(vertical = Dimens.SnackbarBottomPadding),
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
                .padding(horizontal = Dimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(Dimens.ScreenPaddingTop)
            Texts.Headline(
                modifier = Modifier.fillMaxWidth(),
                text = "Let's get \nStarted",
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            TextFields.Name(
                modifier = Modifier,
                value = uiState.name,
                onValueChange = { signUpViewmodel.onNameChange(name = it) },
                isError = uiState.nameError != null
            )
            if (uiState.nameError != null) {
                TextError(text = uiState.nameError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            TextFields.Email(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { signUpViewmodel.onEmailChange(email = it) },
                isError = uiState.emailError != null
            )
            if (uiState.emailError != null) {
                TextError(text = uiState.emailError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            TextFields.Password(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { signUpViewmodel.onPasswordChange(password = it) },
                isError = uiState.passwordError != null
            )
            if (uiState.passwordError != null) {
                TextError(text = uiState.passwordError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            TextFields.ConfirmPassword(
                modifier = Modifier,
                value = uiState.confirmPassword,
                onValueChange = { signUpViewmodel.onConfirmPasswordChange(confirmPassword = it) },
                isError = uiState.passwordConfirmError != null,
                isPasswordMatch = uiState.isPasswordMatch
            )
            if (uiState.passwordConfirmError != null) {
                TextError(text = uiState.passwordConfirmError!!.toUiText().asString())
            }
            Spacer(Modifier.height(25.dp))
            PrimaryButton(
                text = "Sign Up",
                isLoading = uiState.isLoading,
                onClick = { signUpViewmodel.onRegisterClick() }
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            AuthHorizontalDivider(text = "or")
            VerticalSpacer(height = Dimens.SpacingLarge)
            SocialAuthButtons.Google(onClick = {})
            VerticalSpacer(height = Dimens.SpacingLarge)
            AuthTextAction(
                text = "Already have an account?",
                actionText = "Sign in",
                onClick = { onNavigateToLogin() },
            )
        }
    }
}