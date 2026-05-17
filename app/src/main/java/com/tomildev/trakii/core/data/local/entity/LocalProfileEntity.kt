package com.tomildev.trakii.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class LocalProfileEntity(
    @PrimaryKey val id: String,
    val email: String,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    val provider: String,
    @ColumnInfo(name = "is_early_user") val isEarlyUser: Boolean,
    @ColumnInfo(name = "account_type") val accountType: String,
    @ColumnInfo(name = "onboarding_completed") val onboardingCompleted: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String
)