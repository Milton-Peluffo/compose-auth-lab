package com.tomildev.trakii.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthSessionStateRepository {
    fun observeReauthenticationRequired(): Flow<Boolean>
    suspend fun setReauthenticationRequired(required: Boolean)
    fun observePasswordRecoveryInProgress(): Flow<Boolean>
    suspend fun setPasswordRecoveryInProgress(inProgress: Boolean)
}
