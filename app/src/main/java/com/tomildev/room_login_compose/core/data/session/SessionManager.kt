package com.tomildev.room_login_compose.core.data.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
    }

    fun saveSession(userId: Int) {
        prefs.edit().putInt(USER_ID, userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}