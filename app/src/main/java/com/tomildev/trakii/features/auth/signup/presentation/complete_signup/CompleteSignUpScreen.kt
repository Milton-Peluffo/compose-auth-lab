package com.tomildev.trakii.features.auth.signup.presentation.complete_signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.snackbars.AppSnackbarHost
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.textfields.TextFields
import com.tomildev.trakii.core.common.presentation.components.texts.TextError
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun CompleteSignUpScreen(
    modifier: Modifier = Modifier,
    completeSignUpViewModel: CompleteSignUpViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {
    val uiState by completeSignUpViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        completeSignUpViewModel.uiEvents.collect { event ->
            when (event) {
                CompleteSignUpUiEvent.Success -> {
                    onNavigateToHome()
                }

                is CompleteSignUpUiEvent.Error -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Error
                        )
                    )
                }

                is CompleteSignUpUiEvent.Warning -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Warning
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(Dimens.ScreenPaddingTop)

            Texts.Headline(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_complete_signup_title),
            )

            VerticalSpacer(height = Dimens.SpacingExtraLarge)

            TextFields.Name(
                value = uiState.name,
                onValueChange = completeSignUpViewModel::onNameChange,
                isError = uiState.nameError != null
            )
            if (uiState.nameError != null) {
                TextError(text = uiState.nameError!!.toUiText().asString())
            }

            VerticalSpacer(height = Dimens.SpacingLarge)

            TextFields.Password(
                value = uiState.password,
                onValueChange = completeSignUpViewModel::onPasswordChange,
                isError = uiState.passwordError != null
            )
            if (uiState.passwordError != null) {
                TextError(text = uiState.passwordError!!.toUiText().asString())
            }

            VerticalSpacer(height = Dimens.SpacingLarge)

            TextFields.ConfirmPassword(
                value = uiState.confirmPassword,
                onValueChange = completeSignUpViewModel::onConfirmPasswordChange,
                isError = uiState.confirmPasswordError != null,
                isPasswordMatch = uiState.isPasswordMatch
            )
            if (uiState.confirmPasswordError != null) {
                TextError(text = uiState.confirmPasswordError!!.toUiText().asString())
            }

            VerticalSpacer(height = Dimens.SpacingExtraLarge)

            PrimaryButton(
                text = stringResource(R.string.auth_complete_signup_btn_confirm),
                isLoading = uiState.isLoading,
                onClick = completeSignUpViewModel::onCompleteSignUpClick
            )
        }
    }
}