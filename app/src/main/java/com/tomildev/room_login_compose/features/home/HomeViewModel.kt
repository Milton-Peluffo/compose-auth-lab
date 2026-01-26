package com.tomildev.room_login_compose.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomildev.room_login_compose.core.data.session.SessionManager
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun logOut() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1500)
            sessionManager.logout()
            _uiState.update { it.copy(isLoading = false, isLogoutSuccess = true) }
        }
    }

    fun getUserData(email: String) {
        viewModelScope.launch {
            authRepository.getUserByEmail(email).onSuccess { user ->
                user?.let { data ->
                    _uiState.update {
                        it.copy(
                            name = data.name,
                            phone = data.phone,
                            email = data.email,
                            password = data.password,
                        )
                    }
                }
            }
        }
    }
}

data class HomeUiState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLogoutSuccess: Boolean = false,
)