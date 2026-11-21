package com.example.loopit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loopit.domain.model.UserCoordinates
import com.example.loopit.domain.usecase.GetCollectionPointsUseCase
import com.example.loopit.domain.usecase.GetCollectionPointDetailsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionPointViewModel(
    private val getCollectionPointsUseCase: GetCollectionPointsUseCase,
    private val getCollectionPointDetailsUseCase: GetCollectionPointDetailsUseCase
) : ViewModel() {

    private val _mapUiState = MutableStateFlow(CollectionMapUiState())
    val mapUiState = _mapUiState.asStateFlow()

    private val _detailsUiState =
        MutableStateFlow(CollectionPointDetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()

    private var mapJob: Job? = null
    private var detailsJob: Job? = null
    private var selectedPointId: Int? = null

    init {
        loadCollectionPoints()
    }

    fun loadCollectionPoints() {
        mapJob?.cancel()

        mapJob = viewModelScope.launch {
            _mapUiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            try {
                val points = getCollectionPointsUseCase(
                    _mapUiState.value.userLocation
                )

                _mapUiState.update {
                    it.copy(
                        isLoading = false,
                        collectionPoints = points
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _mapUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Unable to load collection points."
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _mapUiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun updateUserLocation(location: UserCoordinates?) {
        if (_mapUiState.value.userLocation == location) return

        _mapUiState.update {
            it.copy(userLocation = location)
        }

        loadCollectionPoints()

        selectedPointId?.let {
            loadCollectionPointDetails(it)
        }
    }

    fun loadCollectionPointDetails(collectionPointId: Int) {
        selectedPointId = collectionPointId
        detailsJob?.cancel()

        detailsJob = viewModelScope.launch {
            _detailsUiState.update {
                it.copy(
                    isLoading = true,
                    collectionPoint = null,
                    errorMessage = null
                )
            }

            try {
                val point = getCollectionPointDetailsUseCase(
                    collectionPointId,
                    _mapUiState.value.userLocation
                )

                _detailsUiState.update {
                    it.copy(
                        isLoading = false,
                        collectionPoint = point,
                        errorMessage = if (point == null) {
                            "Collection point not found."
                        } else {
                            null
                        }
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _detailsUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Unable to load collection point details."
                    )
                }
            }
        }
    }

    fun clearSelectedPoint() {
        selectedPointId = null
        detailsJob?.cancel()
        _detailsUiState.value = CollectionPointDetailsUiState()
    }
}