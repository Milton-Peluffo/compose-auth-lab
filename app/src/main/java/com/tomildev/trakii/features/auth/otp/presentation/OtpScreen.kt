package com.tomildev.trakii.features.auth.otp.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.buttons.BackButton
import com.tomildev.trakii.core.common.presentation.components.buttons.PrimaryButton
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackBars
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarType
import com.tomildev.trakii.core.common.presentation.components.snackbars.SnackbarVisualsCustom
import com.tomildev.trakii.core.common.presentation.components.spacers.HorizontalSpacer
import com.tomildev.trakii.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.core.common.util.ui.UiText
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.features.auth.otp.presentation.components.CustomNumericKeyboard
import com.tomildev.trakii.features.auth.otp.presentation.components.InputDigitBox
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    otpViewModel: OtpViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToCompleteSignUp: (String) -> Unit
) {
    val uiState by otpViewModel.uiState.collectAsStateWithLifecycle()
    val digits by otpViewModel.digitList.collectAsStateWithLifecycle()
    val activeIndex by otpViewModel.activeIndex.collectAsStateWithLifecycle()

    val configuration = LocalConfiguration.current
    val isLandScape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val verticalGap = if (isLandScape) Dimens.SpacingTiny else Dimens.SpacingLarge

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        otpViewModel.uiEvents.collect { event ->
            when (event) {
                OtpUiEvent.NavigateToHome -> onNavigateToHome()
                
                is OtpUiEvent.NavigateToCompleteSignUp -> onNavigateToCompleteSignUp(event.email)

                is OtpUiEvent.Error -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Error
                        )
                    )
                }

                is OtpUiEvent.Warning -> {
                    val errorUiText = event.error.toUiText()
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = errorUiText.title.asString(context),
                            description = errorUiText.description?.asString(context),
                            type = SnackbarType.Warning
                        )
                    )
                }

                OtpUiEvent.CodeResent -> {
                    val successMessage =
                        UiText.StringResource(R.string.auth_shared_otp_code_resent_success)
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = successMessage.asString(context),
                            type = SnackbarType.Success
                        )
                    )
                }
            }
        }
    }

    val otpFormContent = @Composable {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                if (isLandScape) Dimens.SpacingTiny else 0.dp
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BackButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            VerticalSpacer(verticalGap)
            Texts.TitleMedium(
                text = stringResource(R.string.auth_shared_otp_subtitle),
                isSecondary = true,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(verticalGap / 2)
            Texts.TitleMedium(
                text = uiState.displayedEmail,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(verticalGap)

            val hasNetworkError = uiState.networkError != null
            val isInternetError = uiState.networkError == DataError.Network.NoInternet ||
                    uiState.networkError == DataError.Network.Timeout

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                digits.forEachIndexed { index, digit ->
                    InputDigitBox(
                        number = digit,
                        isCursorVisible = index == activeIndex,
                        isSuccess = uiState.isVerified,
                        isError = hasNetworkError && !isInternetError,
                        isWarning = hasNetworkError && isInternetError,
                    )
                }
            }

            VerticalSpacer(verticalGap)

            if (uiState.timer == 0) {
                TextButton(onClick = { otpViewModel.resendOtp() }) {
                    Texts.TitleMedium(text = stringResource(R.string.auth_shared_otp_btn_resend_code))
                }
            } else {
                TextButton(onClick = {}, enabled = false) {
                    Texts.TitleMedium(
                        text = stringResource(R.string.auth_shared_otp_resend_code_in) + " ${uiState.timer}s",
                        isSecondary = true
                    )
                }
            }

            VerticalSpacer(verticalGap)
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.auth_shared_otp_btn_verify),
                onClick = { otpViewModel.verifyOtp() },
                enabled = uiState.isVerifyEnable,
                isLoading = uiState.isLoading,
            )
        }
    }

    val keyboardContent = @Composable {
        Box(contentAlignment = Alignment.Center) {
            CustomNumericKeyboard(
                onNumberClick = { otpViewModel.onNumberClick(it) },
                onDeleteClick = { otpViewModel.onDeleteClick() },
                onClearAll = { otpViewModel.clearAll() }
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(vertical = Dimens.SnackbarBottomPadding),
                hostState = snackbarHostState
            ) { data ->
                val customVisuals = data.visuals as? SnackbarVisualsCustom
                if (customVisuals != null) {
                    when (customVisuals.type) {
                        SnackbarType.Error -> SnackBars.Error(
                            title = customVisuals.message,
                            description = customVisuals.description
                        ) { data.dismiss() }
                        SnackbarType.Warning -> SnackBars.Warning(
                            title = customVisuals.message,
                            description = customVisuals.description
                        ) { data.dismiss() }
                        SnackbarType.Success -> SnackBars.Success(
                            title = customVisuals.message,
                            description = customVisuals.description
                        ) { data.dismiss() }
                        else -> Snackbar(data)
                    }
                } else {
                    Snackbar(data)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = Dimens.ScreenHorizontalPadding
                ),
        ) {
            if (isLandScape) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1.1f)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        otpFormContent()
                    }
                    HorizontalSpacer(Dimens.SpacingLarge)
                    Box(
                        modifier = Modifier
                            .weight(0.9f),
                        contentAlignment = Alignment.Center
                    ) {
                        keyboardContent()
                    }
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    VerticalSpacer(Dimens.ScreenPaddingTop)
                    otpFormContent()
                    VerticalSpacer(Dimens.SpacingLarge)
                    keyboardContent()
                }
            }
        }
    }
}
