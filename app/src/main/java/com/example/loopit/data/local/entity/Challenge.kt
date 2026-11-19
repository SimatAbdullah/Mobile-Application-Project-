package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Challenge")
data class Challenge(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "Title")
    val title: String,

    @ColumnInfo(name = "targetCount")
    val targetCount: Int,

    @ColumnInfo(name = "bonusPoints")
    val bonusPoints: Int
)