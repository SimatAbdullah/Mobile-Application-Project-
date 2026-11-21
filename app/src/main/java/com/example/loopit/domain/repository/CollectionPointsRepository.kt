package com.example.loopit.domain.repository

import com.example.loopit.domain.model.CollectionPointWithDistance
import com.example.loopit.domain.model.UserCoordinates

interface CollectionPointsRepository {

    suspend fun getCollectionPoints(
        userLocation: UserCoordinates?
    ): List<CollectionPointWithDistance>

    suspend fun getCollectionPointDetails(
        collectionPointId: Int,
        userLocation: UserCoordinates?
    ): CollectionPointWithDistance?
}