package com.tomildev.room_login_compose.features.auth.signin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.common.presentation.components.PrimaryTitle
import com.tomildev.room_login_compose.core.common.presentation.components.TextError
import com.tomildev.room_login_compose.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.room_login_compose.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.room_login_compose.core.common.presentation.components.textfields.TextFields
import com.tomildev.room_login_compose.core.common.presentation.mapper.toUiText
import com.tomildev.room_login_compose.features.auth.common.components.AuthHorizontalDivider
import com.tomildev.room_login_compose.features.auth.common.components.AuthTextAction
import com.tomildev.room_login_compose.features.auth.common.components.social.SocialAuthButtons
import com.tomildev.room_login_compose.ui.theme.Dimens

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
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryTitle(
                modifier = Modifier.fillMaxWidth(),
                title = "Hey,Welcome Back!",
            )
            TextFields.Email(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { signInViewModel.onEmailChange(email = it) },
                label = "Email",
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
                label = "Password",
                isError = uiState.passwordError != null
            )
            if (uiState.passwordError != null) {
                TextError(text = uiState.passwordError!!.toUiText().asString())
            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            AuthTextAction(
                text = "Forgot Password?",
                onClick = { },
                horizontalArrangement = Arrangement.End
            )
            VerticalSpacer(height = Dimens.SpacingLarge)
            PrimaryButton(
                text = "Sign in",
                isLoading = uiState.isLoading,
                onClick = { signInViewModel.onLoginClick() }
            )
            VerticalSpacer(height = Dimens.SpacingExtraLarge)
            AuthHorizontalDivider(text = "or")
            VerticalSpacer(height = Dimens.SpacingLarge)
            SocialAuthButtons.Google(onClick = {})
            VerticalSpacer(height = Dimens.SpacingLarge)
            AuthTextAction(
                text = "Don't have an account?",
                actionText = "Sign up",
                onClick = { onNavigateToRegister() },
            )
        }
    }
}