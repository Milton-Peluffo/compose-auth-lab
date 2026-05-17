package com.tomildev.trakii.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tomildev.trakii.core.data.local.entity.LocalProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profiles WHERE id = :userId LIMIT 1")
    suspend fun getProfile(userId: String): LocalProfileEntity?

    @Query("SELECT * FROM profiles WHERE id = :userId LIMIT 1")
    fun observeProfile(userId: String): Flow<LocalProfileEntity?>

    @Upsert
    suspend fun upsertProfile(profile: LocalProfileEntity)

    @Query(
        """
        UPDATE profiles 
        SET onboarding_completed = :completed, updated_at = :updatedAt 
        WHERE id = :userId
        """
    )
    suspend fun updateOnboardingCompleted(
        userId: String,
        completed: Boolean,
        updatedAt: String
    )

    @Query(
        """
        UPDATE profiles 
        SET display_name = :displayName, updated_at = :updatedAt 
        WHERE id = :userId
        """
    )
    suspend fun updateDisplayName(
        userId: String,
        displayName: String,
        updatedAt: String
    )
}
