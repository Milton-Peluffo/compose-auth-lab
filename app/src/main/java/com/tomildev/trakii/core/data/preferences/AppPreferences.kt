package com.tomildev.trakii.core.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
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
class AppPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val APP_APPEARANCE = stringPreferencesKey("app_appearance")
        val LANGUAGE = stringPreferencesKey("language")

    }

    //-------- APP APPEARANCE --------
    val appearance: Flow<String> = context.dataStore.data
        .handleErrors()
        .map { preferences ->
            preferences[PreferencesKeys.APP_APPEARANCE] ?: "system"
        }

    suspend fun setAppearance(appearance: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.APP_APPEARANCE] = appearance
        }
    }

    //-------- LANGUAGE --------
    private val systemLanguage: String
        get() = java.util.Locale.getDefault().language

    val selectedLanguage: Flow<String> = context.dataStore.data
        .handleErrors()
        .map { preferences ->
            preferences[AppPreferences.PreferencesKeys.LANGUAGE] ?: systemLanguage
        }

    suspend fun saveLanguage(langCode: String) {
        context.dataStore.edit { preferences ->
            preferences[AppPreferences.PreferencesKeys.LANGUAGE] = langCode
        }
    }

    private fun Flow<Preferences>.handleErrors(): Flow<Preferences> = this.catch { exception ->
        if (exception is IOException) emit(emptyPreferences()) else throw exception
    }
}