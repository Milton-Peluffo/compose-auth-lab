package com.tomildev.trakii.features.settings.sub_settings.account.data

import com.tomildev.trakii.core.common.util.mappers.mapSupabaseError
import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.settings.sub_settings.account.domain.AccountSettingsRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AccountSettingsRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AccountSettingsRepository {

    override suspend fun updateDisplayName(name: String): Result<Unit, DataError.Network> {
        return try {
            supabaseClient.auth.updateUser {
                data = buildJsonObject {
                    put("display_name", name.trim())
                    put("full_name", name.trim())
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapSupabaseError(e))
        }
    }
}
