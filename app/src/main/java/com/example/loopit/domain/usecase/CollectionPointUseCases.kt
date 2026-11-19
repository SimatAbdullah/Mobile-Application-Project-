package com.example.loopit.domain.usecase

import com.example.loopit.domain.model.CollectionPointWithDistance
import com.example.loopit.domain.model.UserCoordinates
import com.example.loopit.domain.repository.CollectionPointsRepository

class GetCollectionPointsUseCase(
    private val repository: CollectionPointsRepository
) {
    suspend operator fun invoke(
        userLocation: UserCoordinates?
    ): List<CollectionPointWithDistance> {
        return repository.getCollectionPoints(userLocation)
    }
}

class GetCollectionPointDetailsUseCase(
    private val repository: CollectionPointsRepository
) {
    suspend operator fun invoke(
        collectionPointId: Int,
        userLocation: UserCoordinates?
    ): CollectionPointWithDistance? {
        return repository.getCollectionPointDetails(
            collectionPointId,
            userLocation
        )
    }
}
