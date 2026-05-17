package com.tomildev.trakii.features.settings.sub_settings.account.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.common.util.mappers.nowTimestamp
import com.tomildev.trakii.core.data.local.dao.ProfileDao
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.settings.sub_settings.account.domain.AccountSettingsRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AccountSettingsRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userPreferences: UserPreferences,
    private val profileDao: ProfileDao
) : AccountSettingsRepository {

    override suspend fun updateDisplayName(name: String): Result<Unit, DataError.Network> {
        val userId = userPreferences.getCurrentUserId()
            ?: supabaseClient.auth.currentUserOrNull()?.id
            ?: return Result.Error(DataError.Network.Unknown)
        val displayName = name.trim()
        val updatedAt = nowTimestamp()

        profileDao.updateDisplayName(
            userId = userId,
            displayName = displayName,
            updatedAt = updatedAt
        )

        return try {
            supabaseClient.auth.updateUser {
                data = buildJsonObject {
                    put("display_name", displayName)
                    put("full_name", displayName)
                }
            }
            supabaseClient.from("profiles").update(
                {
                    set("display_name", displayName)
                    set("updated_at", updatedAt)
                }
            ) {
                filter { eq("id", userId) }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            when (val error = mapSupabaseError(e)) {
                DataError.Network.NoInternet,
                DataError.Network.Timeout -> Result.Success(Unit)

                else -> Result.Error(error)
            }
        }
    }
}
