
package com.example.loopit.domain.repository

import com.example.loopit.domain.model.RewardsData

interface RewardsRepository {

    // Retrieves rewards information for a specific user
    suspend fun getRewards(userId: String): RewardsData
}
