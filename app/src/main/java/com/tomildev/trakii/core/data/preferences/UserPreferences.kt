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
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")

        val ONBOARDING_COMPLETED =
            booleanPreferencesKey("onboarding_completed")
    }

    //-------- ONBOARDING --------

    val onboardingCompleted: Flow<Boolean> =
        context.dataStore.data
            .handleErrors()
            .map { preferences ->
                preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
            }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
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
            preferences.remove(PreferencesKeys.ONBOARDING_COMPLETED)
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