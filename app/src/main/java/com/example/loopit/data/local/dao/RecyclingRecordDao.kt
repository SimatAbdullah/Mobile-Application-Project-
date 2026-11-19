package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loopit.data.local.entity.RecyclingRecord

@Dao
interface RecyclingRecordDao {

    @Query("SELECT * FROM RecyclingRecord WHERE userId = :userId ORDER BY recycledAt DESC")
    suspend fun getByUser(userId: String): List<RecyclingRecord>

    @Insert
    suspend fun insert(record: RecyclingRecord)

    @Query("SELECT * FROM RecyclingRecord")
    suspend fun getAll(): List<RecyclingRecord>

    @Query("SELECT COUNT(*) FROM RecyclingRecord WHERE userId = :userId")
    suspend fun getTotalRecycled(userId: String): Int

    @Query("SELECT COALESCE(SUM(pointsEarned), 0) FROM RecyclingRecord WHERE userId = :userId")
    suspend fun getTotalPoints(userId: String): Int
}