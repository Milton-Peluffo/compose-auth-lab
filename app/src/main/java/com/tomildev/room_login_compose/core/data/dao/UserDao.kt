package com.tomildev.room_login_compose.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tomildev.room_login_compose.core.data.local.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :uid")
    suspend fun getUserById(uid: Int): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("DELETE FROM users WHERE id = :uid")
    suspend fun deleteUserById(uid: Int)
}