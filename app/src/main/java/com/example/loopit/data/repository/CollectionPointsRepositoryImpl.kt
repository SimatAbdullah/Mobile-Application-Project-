package com.example.loopit.data.repository

import com.example.loopit.data.local.dao.CollectionPointDao
import com.example.loopit.data.local.dao.CollectionPointDeviceDao
import com.example.loopit.data.local.entity.CollectionPoint
import com.example.loopit.domain.model.AcceptedDeviceModel
import com.example.loopit.domain.model.CollectionPointWithDistance
import com.example.loopit.domain.model.UserCoordinates
import com.example.loopit.domain.model.calculateStraightLineDistanceKm
import com.example.loopit.domain.repository.CollectionPointsRepository

class CollectionPointsRepositoryImpl(
    private val collectionPointDao: CollectionPointDao,
    private val collectionPointDeviceDao: CollectionPointDeviceDao
) : CollectionPointsRepository {

    override suspend fun getCollectionPoints(
        userLocation: UserCoordinates?
    ): List<CollectionPointWithDistance> {

        val points = collectionPointDao.getAll()

        return points.map { point ->
            mapCollectionPoint(point, userLocation)
        }.sortedBy {
            it.distanceKm ?: Double.MAX_VALUE
        }
    }

    override suspend fun getCollectionPointDetails(
        collectionPointId: Int,
        userLocation: UserCoordinates?
    ): CollectionPointWithDistance? {

        val point = collectionPointDao.getById(collectionPointId)
            ?: return null

        return mapCollectionPoint(point, userLocation)
    }

    private suspend fun mapCollectionPoint(
        point: CollectionPoint,
        userLocation: UserCoordinates?
    ): CollectionPointWithDistance {

        val acceptedDevices = collectionPointDeviceDao
            .getDevicesByCollectionPoint(point.id)
            .map { device ->
                AcceptedDeviceModel(
                    id = device.id,
                    name = device.name,
                    iconName = device.iconName
                )
            }

        val distanceKm = userLocation?.let { location ->
            calculateStraightLineDistanceKm(
                fromLat = location.latitude,
                fromLng = location.longitude,
                toLat = point.latitude,
                toLng = point.longitude
            )
        }

        return CollectionPointWithDistance(
            id = point.id,
            name = point.name,
            address = point.address,
            latitude = point.latitude,
            longitude = point.longitude,
            distanceKm = distanceKm,
            acceptedDevices = acceptedDevices
        )
    }
}