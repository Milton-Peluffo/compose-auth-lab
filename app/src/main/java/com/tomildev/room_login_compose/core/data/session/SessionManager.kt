package com.tomildev.room_login_compose.core.data.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveSession(email: String) {
        prefs.edit().putString("user_email", email).apply()
    }

    fun getSession(): String? {
        return prefs.getString("user_email", null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}