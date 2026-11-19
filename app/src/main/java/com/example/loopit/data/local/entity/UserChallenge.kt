package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "UserChallenge",
    primaryKeys = ["UserId", "challengeId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["Id"],
            childColumns = ["UserId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Challenge::class,
            parentColumns = ["Id"],
            childColumns = ["challengeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["challengeId"])
    ]
)
data class UserChallenge(

    @ColumnInfo(name = "UserId")
    val userId: String,

    @ColumnInfo(name = "challengeId")
    val challengeId: Int,

    @ColumnInfo(name = "currentProgress", defaultValue = "0")
    val currentProgress: Int = 0,

    @ColumnInfo(name = "status", defaultValue = "'IN_PROGRESS'")
    val status: String = "IN_PROGRESS"
)