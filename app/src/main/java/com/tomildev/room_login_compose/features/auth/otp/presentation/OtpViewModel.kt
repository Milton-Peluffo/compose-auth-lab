package com.tomildev.room_login_compose.features.auth.otp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tomildev.room_login_compose.core.domain.model.error.DataError
import com.tomildev.room_login_compose.core.domain.util.Result
import com.tomildev.room_login_compose.core.navigation.NavRoute
import com.tomildev.room_login_compose.features.auth.otp.domain.OtpRepository
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
    private val otpRepository: OtpRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<OtpUiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()
    private val navArgs = savedStateHandle.toRoute<NavRoute.Otp>()
    private val emailFromArgs = navArgs.email
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

        _uiState.update { it.copy(timer = 10, canResend = false) }

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

            val result = otpRepository.resentOtp(email)
            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    _uiState.update { it.copy(networkError = result.error) }

                    when (result.error) {
                        DataError.Network.NoInternet,
                        DataError.Network.Timeout -> {
                            _uiEvents.send(OtpUiEvent.Warning(result.error))
                        }

                        DataError.Network.InvalidOtp,
                        DataError.Network.ServiceUnavailable -> {
                            _uiEvents.send(OtpUiEvent.Error(result.error))
                        }

                        else -> {
                            _uiEvents.send(OtpUiEvent.Error(DataError.Network.Unknown))
                        }
                    }

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

        println("DEBUG_OTP: Intentando verificar...")
        println("DEBUG_OTP: Email: '${currentState.email}'")
        println("DEBUG_OTP: Código: '${currentState.code}'")

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, networkError = null) }

            val result = otpRepository.verifyOtp(
                email = currentState.email,
                otp = currentState.code
            )

            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    _uiState.update { it.copy(networkError = result.error) }

                    when (result.error) {
                        DataError.Network.NoInternet,
                        DataError.Network.Timeout -> {
                            _uiEvents.send(OtpUiEvent.Warning(result.error))
                        }

                        DataError.Network.ServiceUnavailable,
                        DataError.Network.InvalidOtp -> {
                            _uiEvents.send(OtpUiEvent.Error(result.error))
                        }

                        else -> {
                            _uiEvents.send(OtpUiEvent.Error(DataError.Network.Unknown))
                        }
                    }
                }

                is Result.Success -> {
                    _uiState.update { it.copy(isVerified = true) }
                }
            }
        }
    }

    /**
     * A [StateFlow] representing the OTP code as a list of four individual strings.
     * Each element corresponds to a position in the 4-digit code (indices 0 to 3).
     * If a digit has not been entered yet, the value at that index defaults to an empty string.
     */
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

    /**
     * A [StateFlow] representing the index of the next digit to be entered in the OTP sequence.
     * The value ranges from 0 to 3, corresponding to the current input position.
     * If all 4 digits have been entered, the value becomes -1.
     */
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