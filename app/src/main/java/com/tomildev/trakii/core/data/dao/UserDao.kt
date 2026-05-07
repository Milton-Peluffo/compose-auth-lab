package com.tomildev.trakii.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tomildev.trakii.core.data.local.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :uid")
    suspend fun getUserById(uid: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("DELETE FROM users WHERE id = :uid")
    suspend fun deleteUserById(uid: String)
}
