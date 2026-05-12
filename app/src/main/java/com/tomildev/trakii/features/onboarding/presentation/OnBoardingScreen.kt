package com.tomildev.trakii.features.onboarding.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun OnBoardingScreen(
    onNavigateToHabitList: () -> Unit,
    onboardingViewModel: OnBoardingViewModel = hiltViewModel()
) {
    val uiState by onboardingViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onboardingViewModel.uiEvents.collect { event ->
            when (event) {
                is OnBoardingUiEvent.NavigateToHabitList -> {
                    onNavigateToHabitList()
                }
            }
        }
    }

    Scaffold { innerPadding ->
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
                text = "¡HOLA!, BIENVENIDO A HABITII",
            )
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Comenzar",
                isLoading = uiState.isLoading,
                onClick = {
                    onboardingViewModel.onFinishOnboarding()
                }
            )
        }
    }
}