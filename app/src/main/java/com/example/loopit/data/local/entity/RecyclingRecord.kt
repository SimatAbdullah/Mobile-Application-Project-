package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "RecyclingRecord",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["Id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Device::class,
            parentColumns = ["Id"],
            childColumns = ["deviceId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = CollectionPoint::class,
            parentColumns = ["Id"],
            childColumns = ["CollectionPointId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["deviceId"]),
        Index(value = ["CollectionPointId"])
    ]
)
data class RecyclingRecord(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "deviceId")
    val deviceId: Int,

    @ColumnInfo(name = "CollectionPointId")
    val collectionPointId: Int,

    @ColumnInfo(name = "pointsEarned")
    val pointsEarned: Int,

    @ColumnInfo(name = "recycledAt")
    val recycledAt: Long
)