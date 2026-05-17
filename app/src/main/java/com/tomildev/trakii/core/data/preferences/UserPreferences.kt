package com.tomildev.trakii.core.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val CURRENT_USER_ID = stringPreferencesKey("current_user_id")
    }

    //-------- SESSION --------

    val currentUserId: Flow<String?> =
        context.dataStore.data
            .handleErrors()
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_USER_ID]
            }

    suspend fun setCurrentUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_USER_ID] = userId
        }
    }

    suspend fun getCurrentUserId(): String? = currentUserId.first()

    suspend fun clearLocalSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.CURRENT_USER_ID)
        }
    }

    //-------- ONBOARDING --------

    fun onboardingCompleted(userId: String): Flow<Boolean> =
        context.dataStore.data
            .handleErrors()
            .map { preferences ->
                preferences[onboardingCompletedKey(userId)] ?: false
            }

    suspend fun isOnboardingCompleted(userId: String): Boolean =
        onboardingCompleted(userId).first()

    suspend fun setOnboardingCompleted(userId: String, completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[onboardingCompletedKey(userId)] = completed
        }
    }

    suspend fun isOnboardingSyncPending(userId: String): Boolean =
        context.dataStore.data
            .handleErrors()
            .map { preferences ->
                preferences[onboardingSyncPendingKey(userId)] ?: false
            }
            .first()

    suspend fun setOnboardingSyncPending(userId: String, pending: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[onboardingSyncPendingKey(userId)] = pending
        }
    }

    suspend fun logOut() = clearLocalSession()

    private fun onboardingCompletedKey(userId: String) =
        booleanPreferencesKey("onboarding_completed_$userId")

    private fun onboardingSyncPendingKey(userId: String) =
        booleanPreferencesKey("onboarding_sync_pending_$userId")

    private fun Flow<Preferences>.handleErrors(): Flow<Preferences> = this.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}
