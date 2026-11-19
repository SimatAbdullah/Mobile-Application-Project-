package com.example.loopit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "User",
    indices = [Index(value = ["Email"], unique = true)]
)
data class User(

    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String,

    @ColumnInfo(name = "FullName")
    val fullName: String,

    @ColumnInfo(name = "Email")
    val email: String,

    @ColumnInfo(name = "PasswordHash")
    val passwordHash: String,

    @ColumnInfo(name = "RemindersEnabled", defaultValue = "1")
    val remindersEnabled: Boolean = true,

    @ColumnInfo(name = "PointNotifEnabled", defaultValue = "1")
    val pointNotifEnabled: Boolean = true,

    @ColumnInfo(name = "NewpointsNotifEnabled", defaultValue = "0")
    val newpointsNotifEnabled: Boolean = false
)