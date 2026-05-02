package com.tomildev.trakii.features.auth.signup.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackBars
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.textfields.TextFields
import com.tomildev.trakii.core.common.presentation.components.texts.TextError
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.features.auth.common.components.dividers.AuthHorizontalDivider
import com.tomildev.trakii.features.auth.common.components.buttons.AuthTextAction
import com.tomildev.trakii.features.auth.common.components.buttons.SocialAuthButtons
import com.tomildev.trakii.features.auth.common.util.GoogleAuthClient
import com.tomildev.trakii.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpViewmodel: SignUpViewmodel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToOtp: (String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState by signUpViewmodel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val googleAuthClient = remember { GoogleAuthClient(context) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        signUpViewmodel.uiEvents.collect { uiEvent ->
            when (uiEvent) {
                is SignUpUiEvent.NavigateToOtp -> {
                    onNavigateToOtp(uiEvent.email)
                }

                is SignUpUiEvent.NavigateToHome -> {
                    onNavigateToHome()
                }

                is SignUpUiEvent.Error, is SignUpUiEvent.Warning -> {
                    val (errorData, snackbarType) = when (uiEvent) {
                        is SignUpUiEvent.Error -> uiEvent.error to SnackbarType.Error
                        is SignUpUiEvent.Warning -> uiEvent.error to SnackbarType.Warning
                    }

                    val errorUiText = errorData.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
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
                text = stringResource(R.string.auth_signup_title),
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            TextFields.Email(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { signUpViewmodel.onEmailChange(email = it) },
                isError = uiState.emailError != null
            )
            if (uiState.emailError != null) {
                TextError(text = uiState.emailError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingLarge)
            PrimaryButton(
                text = stringResource(R.string.auth_signup_btn_signup),
                isLoading = uiState.isLoading,
                onClick = { signUpViewmodel.onSignUpClick() }
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            AuthHorizontalDivider()
            VerticalSpacer(height = Dimens.SpacingLarge)
            SocialAuthButtons.Google(
                onClick = {
                    scope.launch {
                        signUpViewmodel.onGoogleSignInStart()
                        val idToken = googleAuthClient.signIn()
                        if (idToken != null) {
                            signUpViewmodel.onGoogleSignIn(idToken)
                        } else {
                            signUpViewmodel.onGoogleSignInCancel()
                        }
                    }
                },
                isLoading = uiState.isGoogleLoading
            )
            VerticalSpacer(height = Dimens.SpacingLarge)
            AuthTextAction(
                text = stringResource(R.string.auth_signup_already_have_account),
                actionText = stringResource(R.string.auth_signup_btn_sign_in),
                onClick = { onNavigateToLogin() },
            )
        }
    }
}