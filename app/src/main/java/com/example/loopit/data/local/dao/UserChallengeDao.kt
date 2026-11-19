package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loopit.data.local.entity.UserChallenge

@Dao
interface UserChallengeDao {

    @Query("SELECT * FROM UserChallenge WHERE UserId = :userId")
    suspend fun getByUser(userId: String): List<UserChallenge>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userChallenge: UserChallenge)

    @Query("""
        UPDATE UserChallenge
        SET currentProgress = :progress
        WHERE UserId = :userId AND challengeId = :challengeId
    """)
    suspend fun updateProgress(
        userId: String,
        challengeId: Int,
        progress: Int
    )

    @Query("""
        UPDATE UserChallenge
        SET status = :status
        WHERE UserId = :userId AND challengeId = :challengeId
    """)
    suspend fun updateStatus(
        userId: String,
        challengeId: Int,
        status: String
    )
}