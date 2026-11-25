
package com.example.loopit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loopit.domain.repository.RewardsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RewardsViewModel(
    private val repository: RewardsRepository
) : ViewModel() {

    // Holds the current rewards screen state
    private val _uiState = MutableStateFlow(RewardsUiState())

    val uiState: StateFlow<RewardsUiState> =
        _uiState.asStateFlow()

    private var loadJob: Job? = null

    // Loads rewards for the current user
    fun loadRewards(userId: String) {

        // Cancel any previous loading operation
        loadJob?.cancel()

        // Validate the current user
        if (userId.isBlank()) {
            _uiState.value = RewardsUiState(
                errorMessage = "Please sign in to view your rewards."
            )
            return
        }

        loadJob = viewModelScope.launch {

            // Display the loading state
            _uiState.value = RewardsUiState(
                isLoading = true
            )

            try {

                // Retrieve rewards from the repository
                val rewards = repository.getRewards(userId)

                // Update the screen with retrieved data
                _uiState.value = RewardsUiState(
                    isLoading = false,
                    rewardsData = rewards
                )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                // Handle errors while loading rewards
                _uiState.value = RewardsUiState(
                    isLoading = false,
                    errorMessage = "Unable to load rewards. Please try again."
                )
            }
        }
    }
}
