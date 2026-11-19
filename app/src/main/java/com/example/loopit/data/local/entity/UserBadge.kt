package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "UserBadge",
    primaryKeys = ["userId", "BadgeId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["Id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Badge::class,
            parentColumns = ["Id"],
            childColumns = ["BadgeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["BadgeId"])
    ]
)
data class UserBadge(

    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "BadgeId")
    val badgeId: Int,

    @ColumnInfo(name = "earnedAt")
    val earnedAt: Long
)