package com.example.loopit

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.loopit.data.local.DatabaseSeeder
import com.example.loopit.data.local.LoopitDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.loopit.data.local.entity.User
import org.junit.Assert.assertNull
import android.database.sqlite.SQLiteConstraintException
import com.example.loopit.data.local.entity.RecyclingRecord
import com.example.loopit.data.local.entity.UserBadge
import com.example.loopit.data.local.entity.UserChallenge
import org.junit.Assert.fail
import kotlinx.coroutines.Dispatchers

@RunWith(AndroidJUnit4::class)
class LoopitDatabaseTest {

    private lateinit var database: LoopitDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry
            .getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(
            context,
            LoopitDatabase::class.java
        )
            .addCallback(DatabaseSeeder)
            .build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun seedDataTest() = runBlocking {
        assertEquals(5, database.deviceDao().getAll().size)
        assertEquals(5, database.collectionPointDao().getAll().size)
        assertEquals(3, database.badgeDao().getAll().size)
        assertEquals(3, database.challengeDao().getAll().size)

        val relationCount = database.collectionPointDao()
            .getAll()
            .sumOf { point ->
                database.collectionPointDeviceDao()
                    .getDevicesByCollectionPoint(point.id).size
            }

        assertEquals(17, relationCount)

    }
    @Test
    fun userDaoTest() = runBlocking {
        val user = User(
            id = "test-user-1",
            fullName = "Test User",
            email = "test@example.com",
            passwordHash = "test_hash"
        )

        // Insert user
        database.userDao().insert(user)

        // Retrieve user
        assertEquals(
            user,
            database.userDao().getByEmail(user.email)
        )

        // Update user
        val updatedUser = user.copy(
            fullName = "Updated User",
            remindersEnabled = false
        )

        database.userDao().update(updatedUser)

        assertEquals(
            updatedUser,
            database.userDao().getByEmail(user.email)
        )

        // Non-existing user
        assertNull(
            database.userDao().getByEmail("unknown@example.com")
        )
    }
    @Test(expected = SQLiteConstraintException::class)
    fun duplicateEmailTest() = runBlocking {
        val firstUser = User(
            id = "user-1",
            fullName = "First User",
            email = "duplicate@example.com",
            passwordHash = "hash1"
        )

        val secondUser = firstUser.copy(
            id = "user-2",
            fullName = "Second User"
        )

        database.userDao().insert(firstUser)
        database.userDao().insert(secondUser)
    }
    @Test
    fun recyclingRecordsTest() = runBlocking {
        val user = User(
            id = "recycling-user",
            fullName = "Test User",
            email = "recycling@example.com",
            passwordHash = "test_hash"
        )

        database.userDao().insert(user)

        val firstRecord = RecyclingRecord(
            userId = user.id,
            deviceId = 1,
            collectionPointId = 1,
            pointsEarned = 30,
            recycledAt = 1000L
        )

        val secondRecord = RecyclingRecord(
            userId = user.id,
            deviceId = 4,
            collectionPointId = 1,
            pointsEarned = 50,
            recycledAt = 2000L
        )

        val dao = database.recyclingRecordDao()

        dao.insert(firstRecord)
        dao.insert(secondRecord)

        assertEquals(2, dao.getAll().size)

        val records = dao.getByUser(user.id)

        assertEquals(2, records.size)
        assertEquals(2000L, records[0].recycledAt)
        assertEquals(1000L, records[1].recycledAt)

        assertEquals(2, dao.getTotalRecycled(user.id))
        assertEquals(80, dao.getTotalPoints(user.id))

        assertEquals(0, dao.getByUser("unknown-user").size)
        assertEquals(0, dao.getTotalRecycled("unknown-user"))
        assertEquals(0, dao.getTotalPoints("unknown-user"))
    }
    @Test
    fun userBadgesTest() = runBlocking {
        val user = User(
            id = "badge-user",
            fullName = "Test User",
            email = "badge@example.com",
            passwordHash = "test_hash"
        )

        database.userDao().insert(user)

        val userBadge = UserBadge(
            userId = user.id,
            badgeId = 1,
            earnedAt = 1000L
        )

        val dao = database.userBadgeDao()

        // Insert badge
        dao.insert(userBadge)

        // Retrieve badge
        val badges = dao.getByUser(user.id)

        assertEquals(1, badges.size)
        assertEquals(userBadge, badges[0])

        // Prevent duplicate badge
        dao.insert(userBadge.copy(earnedAt = 2000L))

        val updatedBadges = dao.getByUser(user.id)

        assertEquals(1, updatedBadges.size)
        assertEquals(1000L, updatedBadges[0].earnedAt)

        // Non-existing user
        assertEquals(0, dao.getByUser("unknown-user").size)
    }
    @Test
    fun userChallengesTest() = runBlocking {
        val user = User(
            id = "challenge-user",
            fullName = "Test User",
            email = "challenge@example.com",
            passwordHash = "test_hash"
        )

        database.userDao().insert(user)

        val userChallenge = UserChallenge(
            userId = user.id,
            challengeId = 1
        )

        val dao = database.userChallengeDao()

        // Insert challenge
        dao.insert(userChallenge)

        val challenges = dao.getByUser(user.id)

        assertEquals(1, challenges.size)
        assertEquals(0, challenges[0].currentProgress)
        assertEquals("IN_PROGRESS", challenges[0].status)

        // Update challenge progress
        dao.updateProgress(user.id, 1, 3)

        assertEquals(
            3,
            dao.getByUser(user.id)[0].currentProgress
        )

        // Update challenge status
        dao.updateStatus(user.id, 1, "COMPLETED")

        assertEquals(
            "COMPLETED",
            dao.getByUser(user.id)[0].status
        )

        // Prevent duplicate challenge
        dao.insert(userChallenge)

        val updatedChallenges = dao.getByUser(user.id)

        assertEquals(1, updatedChallenges.size)
        assertEquals(3, updatedChallenges[0].currentProgress)
        assertEquals("COMPLETED", updatedChallenges[0].status)

        // Non-existing user
        assertEquals(0, dao.getByUser("unknown-user").size)
    }
    @Test
    fun foreignKeysTest() = runBlocking {
        val user = User(
            id = "fk-user",
            fullName = "Test User",
            email = "fk@example.com",
            passwordHash = "test_hash"
        )

        database.userDao().insert(user)

        val validRecord = RecyclingRecord(
            userId = user.id,
            deviceId = 1,
            collectionPointId = 1,
            pointsEarned = 30,
            recycledAt = 1000L
        )

        val dao = database.recyclingRecordDao()

        // Insert valid record
        dao.insert(validRecord)

        val invalidRecords = listOf(
            // Invalid user
            validRecord.copy(userId = "unknown-user"),

            // Invalid device
            validRecord.copy(deviceId = 999),

            // Invalid collection point
            validRecord.copy(collectionPointId = 999)
        )

        for (record in invalidRecords) {
            try {
                dao.insert(record)
                fail("Foreign key constraint was not enforced")
            } catch (e: SQLiteConstraintException) {
                // Expected constraint violation
            }
        }

        // Only the valid record should exist
        assertEquals(1, dao.getAll().size)
    }
    @Test
    fun cascadeDeleteTest() = runBlocking(Dispatchers.IO) {
        val user = User(
            id = "cascade-user",
            fullName = "Test User",
            email = "cascade@example.com",
            passwordHash = "test_hash"
        )

        database.userDao().insert(user)

        // Insert recycling record
        database.recyclingRecordDao().insert(
            RecyclingRecord(
                userId = user.id,
                deviceId = 1,
                collectionPointId = 1,
                pointsEarned = 30,
                recycledAt = 1000L
            )
        )

        // Insert user badge
        database.userBadgeDao().insert(
            UserBadge(
                userId = user.id,
                badgeId = 1,
                earnedAt = 1000L
            )
        )

        // Insert user challenge
        database.userChallengeDao().insert(
            UserChallenge(
                userId = user.id,
                challengeId = 1
            )
        )

        // Verify records before deletion
        assertEquals(
            1,
            database.recyclingRecordDao().getByUser(user.id).size
        )

        assertEquals(
            1,
            database.userBadgeDao().getByUser(user.id).size
        )

        assertEquals(
            1,
            database.userChallengeDao().getByUser(user.id).size
        )

        // Delete user
        database.openHelper.writableDatabase.execSQL(
            "DELETE FROM User WHERE Id = ?",
            arrayOf(user.id)
        )

        // Verify CASCADE deletion
        assertNull(database.userDao().getByEmail(user.email))

        assertEquals(
            0,
            database.recyclingRecordDao().getByUser(user.id).size
        )

        assertEquals(
            0,
            database.userBadgeDao().getByUser(user.id).size
        )

        assertEquals(
            0,
            database.userChallengeDao().getByUser(user.id).size
        )
    }
    @Test
    fun restrictDeleteTest() = runBlocking(Dispatchers.IO) {
        val user = User(
            id = "restrict-user",
            fullName = "Test User",
            email = "restrict@example.com",
            passwordHash = "test_hash"
        )

        database.userDao().insert(user)

        database.recyclingRecordDao().insert(
            RecyclingRecord(
                userId = user.id,
                deviceId = 1,
                collectionPointId = 1,
                pointsEarned = 30,
                recycledAt = 1000L
            )
        )

        val sqlite = database.openHelper.writableDatabase

        // Attempt to delete a referenced device
        try {
            sqlite.execSQL("DELETE FROM Device WHERE Id = 1")
            fail("Device deletion should be restricted")
        } catch (e: SQLiteConstraintException) {
            // Expected constraint violation
        }

        // Attempt to delete a referenced collection point
        try {
            sqlite.execSQL(
                "DELETE FROM CollectionPoint WHERE Id = 1"
            )
            fail("Collection point deletion should be restricted")
        } catch (e: SQLiteConstraintException) {
            // Expected constraint violation
        }

        // Verify that all referenced data still exists
        assertEquals(1, database.deviceDao().getById(1)?.id)
        assertEquals(1, database.collectionPointDao().getById(1)?.id)

        assertEquals(
            1,
            database.recyclingRecordDao().getByUser(user.id).size
        )
    }
    @Test
    fun dataPersistenceTest() = runBlocking(Dispatchers.IO) {
        val context = InstrumentationRegistry
            .getInstrumentation().targetContext

        val dbName = "loopit_persistence_test"

        context.deleteDatabase(dbName)

        val user = User(
            id = "persistence-user",
            fullName = "Test User",
            email = "persistence@example.com",
            passwordHash = "test_hash"
        )

        try {
            val firstDatabase = Room.databaseBuilder(
                context,
                LoopitDatabase::class.java,
                dbName
            ).build()

            try {
                firstDatabase.userDao().insert(user)
            } finally {
                firstDatabase.close()
            }

            val secondDatabase = Room.databaseBuilder(
                context,
                LoopitDatabase::class.java,
                dbName
            ).build()

            try {
                val savedUser = secondDatabase
                    .userDao().getByEmail(user.email)

                assertEquals(user, savedUser)
            } finally {
                secondDatabase.close()
            }

        } finally {
            context.deleteDatabase(dbName)
        }
    }
}

