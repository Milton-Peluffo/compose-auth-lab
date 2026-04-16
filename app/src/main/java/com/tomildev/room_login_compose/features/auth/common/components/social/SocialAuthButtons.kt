package com.tomildev.room_login_compose.features.auth.common.components.social

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tomildev.room_login_compose.R

object SocialAuthButtons {

    @Composable
    fun Google(
        modifier: Modifier = Modifier,
        text: String = stringResource(R.string.auth_btn_google),
        onClick: () -> Unit,
        enabled: Boolean = true,
        isLoading: Boolean = false
    ) {
        SocialAuthButtonBase(
            modifier = modifier,
            text = text,
            onClick = onClick,
            enabled = enabled,
            icon = painterResource(id = R.drawable.ic_google),
            contentDescription = "Continue with Google",
            isLoading = isLoading
        )
    }
}