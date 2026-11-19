package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "CollectionPointDevice",
    primaryKeys = ["collectionPointId", "deviceId"],
    foreignKeys = [
        ForeignKey(
            entity = CollectionPoint::class,
            parentColumns = ["Id"],
            childColumns = ["collectionPointId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Device::class,
            parentColumns = ["Id"],
            childColumns = ["deviceId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["deviceId"])
    ]
)
data class CollectionPointDevice(

    @ColumnInfo(name = "collectionPointId")
    val collectionPointId: Int,

    @ColumnInfo(name = "deviceId")
    val deviceId: Int
)