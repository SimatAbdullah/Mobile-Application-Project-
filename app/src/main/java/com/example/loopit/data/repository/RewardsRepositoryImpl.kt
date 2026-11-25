
package com.example.loopit.data.repository

import com.example.loopit.data.local.LoopitDatabase
import com.example.loopit.data.local.entity.UserBadge
import com.example.loopit.data.local.entity.UserChallenge
import com.example.loopit.domain.model.RewardsData
import com.example.loopit.domain.model.RewardEarningModel
import com.example.loopit.domain.model.RewardBadgeModel
import com.example.loopit.domain.model.RewardChallengeModel
import com.example.loopit.domain.repository.RewardsRepository
import androidx.room.withTransaction

class RewardsRepositoryImpl(
    private val database: LoopitDatabase
) : RewardsRepository {

    private val recyclingRecordDao = database.recyclingRecordDao()
    private val deviceDao = database.deviceDao()
    private val badgeDao = database.badgeDao()
    private val userBadgeDao = database.userBadgeDao()
    private val challengeDao = database.challengeDao()
    private val userChallengeDao = database.userChallengeDao()

    override suspend fun getRewards(
        userId: String
    ): RewardsData = database.withTransaction {

        // Retrieve the user's recycling records
        val records = recyclingRecordDao.getByUser(userId)
        val recycledDevicesCount = records.size

        // Calculate points earned from recycling
        val recyclingPoints = records.sumOf { it.pointsEarned }

        // Retrieve device information
        val devices = deviceDao.getAll().associateBy { it.id }

        // Map recycling records to recent earnings
        val recentEarnings = records.map { record ->
            RewardEarningModel(
                id = record.id,
                deviceName = devices[record.deviceId]?.name
                    ?: "Unknown Device",
                recycledAt = record.recycledAt,
                pointsEarned = record.pointsEarned
            )
        }

        // Retrieve available and earned badges
        val availableBadges = badgeDao.getAll()
        val earnedBadgeIds = userBadgeDao.getByUser(userId)
            .map { it.badgeId }
            .toSet()

        // Award newly achieved badges
        val badges = availableBadges.map { badge ->

            val alreadyEarned = badge.id in earnedBadgeIds

            val isEarned = alreadyEarned ||
                    recycledDevicesCount >= badge.requiredDevices

            if (isEarned && !alreadyEarned) {
                userBadgeDao.insert(
                    UserBadge(
                        userId = userId,
                        badgeId = badge.id,
                        earnedAt = System.currentTimeMillis()
                    )
                )
            }

            RewardBadgeModel(
                id = badge.id,
                title = badge.title,
                requiredDevices = badge.requiredDevices,
                iconName = badge.iconName,
                isEarned = isEarned
            )
        }

        // Retrieve available challenges and saved progress
        val availableChallenges = challengeDao.getAll()

        val savedChallenges = userChallengeDao
            .getByUser(userId)
            .associateBy { it.challengeId }

        // Calculate and save challenge progress
        val challenges = availableChallenges.map { challenge ->

            val savedChallenge = savedChallenges[challenge.id]

            val currentProgress = maxOf(
                recycledDevicesCount,
                savedChallenge?.currentProgress ?: 0
            ).coerceAtMost(challenge.targetCount)

            val isCompleted =
                savedChallenge?.status == "COMPLETED" ||
                        currentProgress >= challenge.targetCount

            val status = if (isCompleted) {
                "COMPLETED"
            } else {
                "IN_PROGRESS"
            }

            if (savedChallenge == null) {
                userChallengeDao.insert(
                    UserChallenge(
                        userId = userId,
                        challengeId = challenge.id,
                        currentProgress = currentProgress,
                        status = status
                    )
                )
            } else {
                userChallengeDao.updateProgress(
                    userId = userId,
                    challengeId = challenge.id,
                    progress = currentProgress
                )

                userChallengeDao.updateStatus(
                    userId = userId,
                    challengeId = challenge.id,
                    status = status
                )
            }

            RewardChallengeModel(
                id = challenge.id,
                title = challenge.title,
                currentProgress = currentProgress,
                targetCount = challenge.targetCount,
                bonusPoints = challenge.bonusPoints,
                isCompleted = isCompleted
            )
        }

        // Calculate bonus points from completed challenges
        val bonusPoints = challenges
            .filter { it.isCompleted }
            .sumOf { it.bonusPoints }

        // Return the user's complete rewards information
        RewardsData(
            totalPoints = recyclingPoints + bonusPoints,
            recycledDevicesCount = recycledDevicesCount,
            recentEarnings = recentEarnings,
            badges = badges,
            challenges = challenges
        )
    }
}
