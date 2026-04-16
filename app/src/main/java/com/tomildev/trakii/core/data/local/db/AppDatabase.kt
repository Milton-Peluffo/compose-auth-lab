package com.tomildev.trakii.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tomildev.trakii.core.data.dao.UserDao
import com.tomildev.trakii.core.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}