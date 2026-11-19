package com.example.loopit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.loopit.data.local.entity.*
import com.example.loopit.data.local.dao.*

@Database(
    entities = [
        User::class,
        Device::class,
        CollectionPoint::class,
        RecyclingRecord::class,
        Badge::class,
        UserBadge::class,
        Challenge::class,
        UserChallenge::class,
        CollectionPointDevice::class
    ],
    version = 1,
    exportSchema = true
)
abstract class LoopitDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun deviceDao(): DeviceDao

    abstract fun collectionPointDao(): CollectionPointDao

    abstract fun recyclingRecordDao(): RecyclingRecordDao

    abstract fun badgeDao(): BadgeDao

    abstract fun userBadgeDao(): UserBadgeDao

    abstract fun challengeDao(): ChallengeDao

    abstract fun userChallengeDao(): UserChallengeDao

    abstract fun collectionPointDeviceDao(): CollectionPointDeviceDao

    companion object {

        @Volatile
        private var INSTANCE: LoopitDatabase? = null

        fun getDatabase(context: Context): LoopitDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LoopitDatabase::class.java,
                    "loopit_database"
                )
                    .addCallback(DatabaseSeeder)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}