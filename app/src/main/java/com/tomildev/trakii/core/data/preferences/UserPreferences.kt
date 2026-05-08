package com.tomildev.trakii.core.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val REAUTHENTICATION_REQUIRED = booleanPreferencesKey("reauthentication_required")
        val PASSWORD_RECOVERY_IN_PROGRESS = booleanPreferencesKey("password_recovery_in_progress")
    }

    //-------- LANGUAGE --------
    private val systemLanguage: String
        get() = java.util.Locale.getDefault().language

    val selectedLanguage: Flow<String> = context.dataStore.data
        .handleErrors()
        .map { preferences ->
            preferences[PreferencesKeys.LANGUAGE] ?: systemLanguage
        }

    suspend fun saveLanguage(langCode: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = langCode
        }
    }

    suspend fun logOut() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val isReauthenticationRequired: Flow<Boolean> = context.dataStore.data
        .handleErrors()
        .map { preferences ->
            preferences[PreferencesKeys.REAUTHENTICATION_REQUIRED] ?: false
        }

    suspend fun setReauthenticationRequired(required: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.REAUTHENTICATION_REQUIRED] = required
        }
    }

    val isPasswordRecoveryInProgress: Flow<Boolean> = context.dataStore.data
        .handleErrors()
        .map { preferences ->
            preferences[PreferencesKeys.PASSWORD_RECOVERY_IN_PROGRESS] ?: false
        }

    suspend fun setPasswordRecoveryInProgress(inProgress: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PASSWORD_RECOVERY_IN_PROGRESS] = inProgress
        }
    }

    //-------- CONFIGURATION SECTION --------
    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .handleErrors()
        .map { preferences ->
            preferences[PreferencesKeys.DARK_MODE] ?: false
        }

    suspend fun toggleDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = isDark
        }
    }

    private fun Flow<Preferences>.handleErrors(): Flow<Preferences> = this.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}
