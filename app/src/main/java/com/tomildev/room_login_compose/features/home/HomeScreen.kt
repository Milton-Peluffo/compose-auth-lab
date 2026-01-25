package com.tomildev.room_login_compose.features.home

import android.provider.ContactsContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.room_login_compose.core.presentation.components.PrimaryButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryIconButton
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTextField
import com.tomildev.room_login_compose.core.presentation.components.PrimaryTitle
import java.util.Locale.getDefault

@Composable
fun HomeScreen(
    email: String,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserData(email)
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val userName: String = uiState.name.uppercase(getDefault())
            PrimaryTitle(
                title = "HI AGAIN $userName",
                subtitle = "My information"
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.name,
                onValueChange = { },
                label = "Name",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.phone,
                onValueChange = { },
                label = "Phone",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.email,
                onValueChange = { },
                label = "Email",
            )
            PrimaryTextField(
                modifier = Modifier,
                value = uiState.password,
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