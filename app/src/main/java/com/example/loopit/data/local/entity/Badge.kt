package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Badge",
    indices = [Index(value = ["title"], unique = true)]
)
data class Badge(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "requiredDevices")
    val requiredDevices: Int,

    @ColumnInfo(name = "IconName")
    val iconName: String
)