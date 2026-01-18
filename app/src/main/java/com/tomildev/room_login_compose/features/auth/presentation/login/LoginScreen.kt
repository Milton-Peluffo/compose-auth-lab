package com.tomildev.room_login_compose.features.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomildev.compose_samples.views.textFields.basic_login.components.TextFieldPrimary
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTitle
import com.tomildev.room_login_compose.features.auth.presentation.components.ButtomPrimary

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTitle(
            title = "WELCOME BACK!",
            subtitle = "LOGIN"
        )
        TextFieldPrimary(
            modifier = Modifier,
            value = "",
            onValueChange = { "" },
            label = "Email"
        )
        Spacer(Modifier.height(10.dp))
        TextFieldPrimary(
            modifier = Modifier,
            value = "",
            onValueChange = { "" },
            label = "Password"
        )
        Spacer(Modifier.height(20.dp))
        ButtomPrimary(
            text = "Login",
            onClick = {}
        )
        Spacer(Modifier.height(20.dp))
        AuthTextAction(
            text = "I forgot my password",
            onClick = { }
        )
    }
}