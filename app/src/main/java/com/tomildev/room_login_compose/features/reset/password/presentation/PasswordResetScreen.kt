package com.tomildev.room_login_compose.features.reset.password.presentation

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
import androidx.compose.ui.unit.dp
import com.tomildev.room_login_compose.core.common.presentation.components.buttons.BackButton
import com.tomildev.room_login_compose.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.room_login_compose.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.room_login_compose.core.common.presentation.components.textfields.TextFields
import com.tomildev.room_login_compose.core.common.presentation.components.texts.Texts
import com.tomildev.room_login_compose.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding( horizontal = Dimens.ScreenHorizontalPadding),
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
                text = "Create,\nNew password",
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
                text = "Confirm",
                isLoading = false,
                onClick = { }
            )
        }
    }
}