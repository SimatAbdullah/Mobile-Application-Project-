
package com.example.loopit.ui.viewmodel

import com.example.loopit.domain.model.RewardsData

// Represents the current state of the rewards screen
data class RewardsUiState(
    val isLoading: Boolean = false,
    val rewardsData: RewardsData = RewardsData(),
    val errorMessage: String? = null
)
