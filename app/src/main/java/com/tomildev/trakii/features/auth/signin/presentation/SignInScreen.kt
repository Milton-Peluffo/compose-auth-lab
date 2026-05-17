package com.tomildev.trakii.features.auth.signin.presentation

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.snackbars.AppSnackbarHost
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.signin.presentation.components.buttons.SocialAuthButtons
import com.tomildev.trakii.features.auth.signin.util.GoogleAuthClient
import com.tomildev.trakii.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToHabitList: () -> Unit,
    onNavigateToOnBoarding: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val googleAuthClient = remember { GoogleAuthClient(context) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        signInViewModel.uiEvents.collect { uiEvent ->
            when (uiEvent) {
                SignInUiEvent.NavigateToHabitList -> onNavigateToHabitList()
                SignInUiEvent.NavigateToOnBoarding -> onNavigateToOnBoarding()

                is SignInUiEvent.Error, is SignInUiEvent.Warning -> {
                    val (errorData, snackbarType) = if (uiEvent is SignInUiEvent.Error) {
                        uiEvent.error to SnackbarType.Error
                    } else {
                        (uiEvent as SignInUiEvent.Warning).error to SnackbarType.Warning
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
            AppSnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
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
            SocialAuthButtons.Google(
                onClick = {
                    scope.launch {
                        signInViewModel.onGoogleSignInStart()
                        when (val result = googleAuthClient.signIn()) {

                            is Result.Success -> {

                                val idToken = result.data

                                if (idToken != null) {
                                    signInViewModel.onGoogleSignIn(idToken)
                                } else {
                                    signInViewModel.onGoogleSignInCancelled()
                                }
                            }

                            is Result.Error -> {
                                signInViewModel.onGoogleSignInError(result.error)
                            }
                        }
                    }
                },
                isLoading = uiState.isGoogleLoading
            )
        }
    }
}
