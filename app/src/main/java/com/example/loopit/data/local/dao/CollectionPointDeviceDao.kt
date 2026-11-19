package com.example.loopit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loopit.data.local.entity.CollectionPointDevice
import com.example.loopit.data.local.entity.Device

@Dao
interface CollectionPointDeviceDao {

    @Query("""
        SELECT Device.*
        FROM Device
        INNER JOIN CollectionPointDevice
        ON Device.Id = CollectionPointDevice.deviceId
        WHERE CollectionPointDevice.collectionPointId = :collectionPointId
    """)
    suspend fun getDevicesByCollectionPoint(
        collectionPointId: Int
    ): List<Device>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(relation: CollectionPointDevice)
}