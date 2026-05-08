package com.tomildev.trakii.core.data.repository

import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.repository.AuthSessionStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthSessionStateRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences
) : AuthSessionStateRepository {

    override fun observeReauthenticationRequired(): Flow<Boolean> {
        return userPreferences.isReauthenticationRequired
    }

    override suspend fun setReauthenticationRequired(required: Boolean) {
        userPreferences.setReauthenticationRequired(required)
    }

    override fun observePasswordRecoveryInProgress(): Flow<Boolean> {
        return userPreferences.isPasswordRecoveryInProgress
    }

    override suspend fun setPasswordRecoveryInProgress(inProgress: Boolean) {
        userPreferences.setPasswordRecoveryInProgress(inProgress)
    }
}
