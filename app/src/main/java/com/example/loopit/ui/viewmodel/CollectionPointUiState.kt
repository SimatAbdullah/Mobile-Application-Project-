package com.example.loopit.ui.viewmodel

import com.example.loopit.domain.model.CollectionPointWithDistance
import com.example.loopit.domain.model.UserCoordinates

data class CollectionMapUiState(
    val isLoading: Boolean = true,
    val collectionPoints: List<CollectionPointWithDistance> = emptyList(),
    val searchQuery: String = "",
    val userLocation: UserCoordinates? = null,
    val errorMessage: String? = null
) {
    val filteredPoints: List<CollectionPointWithDistance>
        get() = collectionPoints.filter { point ->
            point.name.contains(searchQuery, ignoreCase = true) ||
                    point.address.contains(searchQuery, ignoreCase = true)
        }
}

data class CollectionPointDetailsUiState(
    val isLoading: Boolean = true,
    val collectionPoint: CollectionPointWithDistance? = null,
    val errorMessage: String? = null
)