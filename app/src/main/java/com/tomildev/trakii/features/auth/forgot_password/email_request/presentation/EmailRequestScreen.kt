package com.tomildev.trakii.features.auth.forgot_password.email_request.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.tomildev.trakii.core.common.presentation.components.topbars.BackButtonTopBar
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailRequestScreen(
    modifier: Modifier = Modifier,
    emailRequestViewmodel: EmailRequestViewmodel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToOtp: (String) -> Unit,
) {

    val context = LocalContext.current
    val uiState by emailRequestViewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        emailRequestViewmodel.uiEvents.collect { uiEvent ->
            when (uiEvent) {
                is EmailRequestUiEvent.NavigateToOtp -> {
                    onNavigateToOtp(uiEvent.email)
                }

                is EmailRequestUiEvent.Error, is EmailRequestUiEvent.Warning -> {
                    val (errorData, snackbarType) = when (uiEvent) {
                        is EmailRequestUiEvent.Error -> uiEvent.error to SnackbarType.Error
                        is EmailRequestUiEvent.Warning -> uiEvent.error to SnackbarType.Warning
                    }

                    if (errorData != DataError.Network.InvalidCredentials) {
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
    }

    Scaffold(
        topBar = {
            BackButtonTopBar(
                backButton = { onNavigateToSignIn() }
            )
        },
        snackbarHost = {
            AppSnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->

        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.ScreenHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(Dimens.ScreenPaddingTop)
            Texts.Headline(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_email_request_password_reset_title),
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            Texts.Body(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_email_request_password_reset_subtitle),
                isSecondary = true,
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            TextFields.Email(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { emailRequestViewmodel.onEmailChange(email = it) },
                isError = uiState.emailError != null
            )
            if (uiState.emailError != null) {
                TextError(text = uiState.emailError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingLarge)
            PrimaryButton(
                text = stringResource(R.string.common_btn_send),
                isLoading = uiState.isLoading,
                onClick = { emailRequestViewmodel.onSendOtpVerificationRequest() }
            )
        }
    }
}