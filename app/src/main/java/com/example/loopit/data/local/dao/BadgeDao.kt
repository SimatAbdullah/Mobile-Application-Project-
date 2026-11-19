package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loopit.data.local.entity.Badge

@Dao
interface BadgeDao {

    @Query("SELECT * FROM Badge")
    suspend fun getAll(): List<Badge>

    @Query("SELECT * FROM Badge WHERE Id = :id LIMIT 1")
    suspend fun getById(id: Int): Badge?

    @Insert
    suspend fun insert(badge: Badge)
}