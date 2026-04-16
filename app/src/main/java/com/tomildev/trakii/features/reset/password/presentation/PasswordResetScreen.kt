package com.tomildev.trakii.features.reset.password.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.buttons.BackButton
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.textfields.TextFields
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalPadding),
                title = {},
                navigationIcon = { BackButton(onClick = { onNavigateToSignIn() }) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    scrolledContainerColor = androidx.compose.ui.graphics.Color.Transparent
                ),
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
                value = "",
                onValueChange = {},
                isError = false
            )
//            if (uiState.passwordError != null) {
//                TextError(text = uiState.passwordError!!.toUiText().asString())
//            }
            VerticalSpacer(height = Dimens.SpacingMedium)
            TextFields.ConfirmPassword(
                modifier = Modifier,
                value = "",
                onValueChange = {},
                isError = false
            )
//            if (uiState.passwordError != null) {
//                TextError(text = uiState.passwordError!!.toUiText().asString())
//            }
            VerticalSpacer(height = Dimens.SpacingLarge)
            PrimaryButton(
                text = stringResource(R.string.common_btn_confirm),
                isLoading = false,
                onClick = { }
            )
        }
    }
}