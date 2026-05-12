package com.tomildev.trakii.features.auth.signin.util

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.tomildev.trakii.BuildConfig
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleAuthClient(
    private val context: Context,
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(): Result<String?, DataError.Network> {
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
                    is GoogleIdTokenCredential -> credential.idToken
                    else -> {
                        credential.data.getString(
                            "com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN"
                        ) ?: return@withContext Result.Error(DataError.Network.Unknown)
                    }
                }
                Result.Success(idToken)
            } catch (e: GetCredentialCancellationException) {
                Result.Success(null)
            } catch (e: Exception) {
                e.printStackTrace()
                val error = when {
                    e is GetCredentialException && (
                            e.message?.contains("28404") == true
                            ) -> DataError.Network.NoInternet

                    else -> DataError.Network.Unknown
                }
                Result.Error(error)
            }
        }
    }
}