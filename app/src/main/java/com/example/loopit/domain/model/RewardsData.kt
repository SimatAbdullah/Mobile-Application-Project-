
package com.example.loopit.domain.model

// Represents the user's rewards information
data class RewardsData(
    val totalPoints: Int = 0,
    val recycledDevicesCount: Int = 0,
    val recentEarnings: List<RewardEarningModel> = emptyList(),
    val badges: List<RewardBadgeModel> = emptyList(),
    val challenges: List<RewardChallengeModel> = emptyList()
)

// Represents points earned from a recycling record
data class RewardEarningModel(
    val id: Int,
    val deviceName: String,
    val recycledAt: Long,
    val pointsEarned: Int
)

// Represents a badge and its achievement status
data class RewardBadgeModel(
    val id: Int,
    val title: String,
    val requiredDevices: Int,
    val iconName: String,
    val isEarned: Boolean
)

// Represents a challenge and the user's progress
data class RewardChallengeModel(
    val id: Int,
    val title: String,
    val currentProgress: Int,
    val targetCount: Int,
    val bonusPoints: Int,
    val isCompleted: Boolean
)
