package com.tomildev.room_login_compose.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}