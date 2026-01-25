package com.tomildev.room_login_compose.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomildev.room_login_compose.core.presentation.components.PrimaryButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryIconButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTitle
import com.tomildev.room_login_compose.core.presentation.components.TextError
import com.tomildev.room_login_compose.features.auth.presentation.components.AuthTextAction

@Composable
fun HomeScreen() {

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryTitle(
                title = "HI AGAIN ",
                subtitle = "My information"
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { },
                label = "Name",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { },
                label = "Phone",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { },
                label = "Email",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { },
                label = "Password",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = "",
                onValueChange = { },
                label = "Confirm password",
            )
//            uiState.errorMessage?.let { error ->
//                TextError(text = error)
//            }
            Spacer(Modifier.height(20.dp))
            PrimaryButton(
                text = "Edit my information",
                onClick = { }
            )
            Spacer(Modifier.height(20.dp))
            PrimaryIconButton(text = "Log out", onClick = {})
        }
    }
}