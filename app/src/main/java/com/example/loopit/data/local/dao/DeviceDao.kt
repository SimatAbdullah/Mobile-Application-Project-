package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loopit.data.local.entity.Device

@Dao
interface DeviceDao {

    @Query("SELECT * FROM Device")
    suspend fun getAll(): List<Device>

    @Query("SELECT * FROM Device WHERE Id = :id LIMIT 1")
    suspend fun getById(id: Int): Device?

    @Insert
    suspend fun insert(device: Device)
}