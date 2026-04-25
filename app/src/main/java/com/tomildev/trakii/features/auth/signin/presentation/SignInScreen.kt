package com.tomildev.trakii.features.auth.signin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.textfields.TextFields
import com.tomildev.trakii.core.common.presentation.components.texts.TextError
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.features.auth.common.components.AuthHorizontalDivider
import com.tomildev.trakii.features.auth.common.components.AuthTextAction
import com.tomildev.trakii.features.auth.common.components.buttons.SocialAuthButtons
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: (String) -> Unit
) {

    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()

//    LaunchedEffect(uiState.isLoginSuccess) {
//        if (uiState.isLoginSuccess) {
//            onNavigateToHome(uiState.email)
//        }
//    }

    Scaffold { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Texts.Headline(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_signin_title),
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            TextFields.Email(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { signInViewModel.onEmailChange(email = it) },
                isError = uiState.emailError != null
            )
            if (uiState.emailError != null) {
                TextError(text = uiState.emailError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            TextFields.Password(
                modifier = Modifier,
                value = uiState.password,
                onValueChange = { signInViewModel.onPasswordChange(password = it) },
                isError = uiState.passwordError != null
            )
            if (uiState.passwordError != null) {
                TextError(text = uiState.passwordError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            AuthTextAction(
                text = stringResource(R.string.auth_signin_btn_forgot_password),
                onClick = { },
                horizontalArrangement = Arrangement.End
            )
            VerticalSpacer(height = Dimens.SpacingLarge)
            PrimaryButton(
                text = stringResource(R.string.auth_signin_btn),
                isLoading = uiState.isLoading,
                onClick = { signInViewModel.onLoginClick() }
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            AuthHorizontalDivider()
            VerticalSpacer(height = Dimens.SpacingLarge)
            SocialAuthButtons.Google(onClick = {})
            VerticalSpacer(height = Dimens.SpacingLarge)
            AuthTextAction(
                text = stringResource(R.string.auth_signin_dont_have_account),
                actionText = stringResource(R.string.auth_signin_btn_sign_up),
                onClick = { onNavigateToRegister() },
            )
        }
    }
}