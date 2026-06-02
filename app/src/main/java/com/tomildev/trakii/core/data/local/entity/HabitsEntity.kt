package com.tomildev.trakii.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "user_id") val userId: String,
    val title: String,
    val description: String?,
    @ColumnInfo(name = "icon_name") val iconName: String,
    @ColumnInfo(name = "color_id") val colorId: String,
    @ColumnInfo(name = "times_per_day") val timesPerDay: Int = 1,
    @ColumnInfo(name = "is_synced") val isSynced: Boolean = false,
    @ColumnInfo(name = "current_streak") val currentStreak: Int = 0,
    @ColumnInfo(name = "best_streak") val bestStreak: Int = 0,
    @ColumnInfo(name = "estimated_time") val estimatedTime: Int = 0,
    @ColumnInfo(name = "is_archived") val isArchived: Boolean = false,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String
)