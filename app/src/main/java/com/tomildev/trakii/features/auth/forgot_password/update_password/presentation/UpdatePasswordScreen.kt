package com.tomildev.trakii.features.auth.forgot_password.update_password.presentation

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
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordScreen(
    modifier: Modifier = Modifier,
    updatePasswordViewModel: UpdatePasswordViewModel = hiltViewModel(),
    onNavigateToSignIn: (Boolean) -> Unit
) {

    val uiState by updatePasswordViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        updatePasswordViewModel.uiEvents.collect { event ->
            when (event) {
                UpdatePasswordUiEvent.Success -> {
                    onNavigateToSignIn(true)
                }

                is UpdatePasswordUiEvent.Error -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Error
                        )
                    )
                }

                is UpdatePasswordUiEvent.Warning -> {
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
        topBar = {
            BackButtonTopBar(
                backButton = { onNavigateToSignIn(false) }
            )
        },
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
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
                text = stringResource(R.string.auth_shared_password_reset_title),
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            Texts.Body(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_shared_password_reset_subtitle),
                isSecondary = true,
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            TextFields.Password(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { updatePasswordViewModel.onPasswordChange(it) },
                isError = uiState.passwordError != null
            )
            if (uiState.passwordError != null) {
                TextError(text = uiState.passwordError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            TextFields.ConfirmPassword(
                modifier = Modifier,
                value = uiState.confirmPassword,
                onValueChange = { updatePasswordViewModel.onConfirmPasswordChange(it) },
                isError = uiState.confirmPasswordError != null,
                isPasswordMatch = uiState.isPasswordMatch
            )
            if (uiState.confirmPasswordError != null) {
                TextError(text = uiState.confirmPasswordError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingLarge)
            PrimaryButton(
                text = stringResource(R.string.common_btn_confirm),
                isLoading = uiState.isLoading,
                onClick = { updatePasswordViewModel.onConfirmChanges() }
            )
        }
    }
}
