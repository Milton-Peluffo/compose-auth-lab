package com.tomildev.trakii.features.auth.common.util

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.tomildev.trakii.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleAuthClient(
    private val context: Context,
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(): String? {
        return withContext(Dispatchers.IO) {
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .setAutoSelectEnabled(false)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                val idToken = when (val credential = result.credential) {
                    is GoogleIdTokenCredential -> {
                        credential.idToken
                    }
                    else -> {
                        credential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN")
                    }
                }
                idToken
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}