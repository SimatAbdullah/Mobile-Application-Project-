package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loopit.data.local.entity.UserBadge

@Dao
interface UserBadgeDao {

    @Query("SELECT * FROM UserBadge WHERE userId = :userId")
    suspend fun getByUser(userId: String): List<UserBadge>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userBadge: UserBadge)
}