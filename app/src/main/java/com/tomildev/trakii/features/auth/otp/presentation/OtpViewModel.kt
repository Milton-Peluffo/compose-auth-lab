package com.tomildev.trakii.features.auth.otp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.core.navigation.NavRoute
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import com.tomildev.trakii.features.auth.otp.domain.OtpVerificationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<OtpUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val navArgs = savedStateHandle.toRoute<NavRoute.Otp>()
    private val emailFromArgs = navArgs.email
    private val isRecovery = navArgs.isRecovery
    private var timerJob: Job? = null

    init {
        _uiState.update {
            it.copy(
                email = emailFromArgs,
                displayedEmail = maskEmail(emailFromArgs)
            )
        }
        startTimer()
    }

    private fun maskEmail(email: String): String {
        val atIndex = email.indexOf('@')
        if (atIndex <= 1) return email

        val name = email.substring(0, atIndex)
        val domain = email.substring(atIndex)

        return when {
            name.length > 4 -> "${name.take(3)}****${name.last()}$domain"
            name.length == 4 -> "${name.take(2)}**${name.last()}$domain"
            else -> "${name.first()}**$domain"
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(timer = 60, canResend = false) }

        timerJob = viewModelScope.launch {
            while (_uiState.value.timer > 0) {
                delay(1000L)
                _uiState.update { it.copy(timer = it.timer - 1) }
            }
            _uiState.update { it.copy(canResend = true) }
        }
    }

    fun resendOtp() {
        val email = _uiState.value.email
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, networkError = null) }

            val result = if (isRecovery) {
                authUseCases.sendResetOtp(email)
            } else {
                authUseCases.resendOtp.execute(email)
            }

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    _uiState.update { it.copy(networkError = result.error) }
                    sendErrorEvent(result.error)
                }

                is Result.Success -> {
                    startTimer()
                    _uiEvents.send(OtpUiEvent.CodeResent)
                }
            }
        }
    }

    fun verifyOtp() {
        val currentState = _uiState.value
        if (currentState.code.length < 6) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, networkError = null) }

            val result = authUseCases.verifyOtp.execute(
                email = currentState.email,
                otp = currentState.code
            )

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    _uiState.update { it.copy(networkError = result.error) }
                    sendErrorEvent(result.error)
                }

                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isVerified = true,
                            verificationResult = result.data
                        )
                    }

                    if (isRecovery) {
                        _uiEvents.send(OtpUiEvent.NavigateToUpdatePassword)
                        return@launch
                    }

                    when (result.data) {
                        OtpVerificationResult.NewUser -> {
                            _uiEvents.send(OtpUiEvent.NavigateToCompleteSignUp(currentState.email))
                        }

                        OtpVerificationResult.UserExists -> {
                            _uiEvents.send(OtpUiEvent.NavigateToHome)
                        }
                    }
                }
            }
        }
    }

    private suspend fun sendErrorEvent(error: DataError.Network) {
        when (error) {
            DataError.Network.NoInternet,
            DataError.Network.Timeout -> {
                _uiEvents.send(OtpUiEvent.Warning(error))
            }

            else -> {
                _uiEvents.send(OtpUiEvent.Error(error))
            }
        }
    }

    val digitList: StateFlow<List<String>> = _uiState
        .map { state ->
            (0..5).map { index ->
                state.code.getOrNull(index)?.toString() ?: ""
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("", "", "", "", "", "")
        )

    val activeIndex: StateFlow<Int> = _uiState
        .map { state ->
            if (state.code.length < 6) state.code.length else -1
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun onNumberClick(number: String) {
        val currentCode = _uiState.value.code
        if (currentCode.length < 6) {
            val newCode = currentCode + number
            _uiState.update {
                it.copy(code = newCode, networkError = null, isVerifyEnable = newCode.length == 6)
            }
        }
    }

    fun onDeleteClick() {
        val currentCode = _uiState.value.code
        if (currentCode.isNotEmpty()) {
            val newCode = currentCode.dropLast(1)
            _uiState.update {
                it.copy(code = newCode, isVerifyEnable = false)
            }
        }
    }

    fun clearAll() {
        _uiState.update {
            it.copy(code = "", isVerifyEnable = false)
        }
    }
}