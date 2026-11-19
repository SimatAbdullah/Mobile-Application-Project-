package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loopit.data.local.entity.CollectionPoint

@Dao
interface CollectionPointDao {

    @Query("SELECT * FROM CollectionPoint")
    suspend fun getAll(): List<CollectionPoint>

    @Query("SELECT * FROM CollectionPoint WHERE Id = :id LIMIT 1")
    suspend fun getById(id: Int): CollectionPoint?

    @Insert
    suspend fun insert(point: CollectionPoint)
}