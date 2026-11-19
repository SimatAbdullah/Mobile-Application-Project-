package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loopit.data.local.entity.Challenge

@Dao
interface ChallengeDao {

    @Query("SELECT * FROM Challenge")
    suspend fun getAll(): List<Challenge>

    @Query("SELECT * FROM Challenge WHERE Id = :id LIMIT 1")
    suspend fun getById(id: Int): Challenge?

    @Insert
    suspend fun insert(challenge: Challenge)
}
