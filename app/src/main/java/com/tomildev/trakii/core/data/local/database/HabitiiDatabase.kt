package com.tomildev.trakii.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tomildev.trakii.core.data.local.dao.ProfileDao
import com.tomildev.trakii.core.data.local.entity.LocalProfileEntity

@Database(
    entities = [
        LocalProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HabitiiDatabase : RoomDatabase() {
    abstract val profileDao: ProfileDao
}