package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Device",
    indices = [Index(value = ["Name"], unique = true)]
)
data class Device(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "pointValue")
    val pointValue: Int,

    @ColumnInfo(name = "DisposalGuidance")
    val disposalGuidance: String,

    @ColumnInfo(name = "IconName")
    val iconName: String
)
