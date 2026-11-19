package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.loopit.data.local.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE Email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User?

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)
}