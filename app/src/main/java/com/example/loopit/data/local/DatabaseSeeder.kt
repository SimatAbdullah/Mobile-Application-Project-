
package com.example.loopit.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseSeeder : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        seedDevices(db)
        seedCollectionPoints(db)
        seedBadges(db)
        seedChallenges(db)
        seedCollectionPointDevices(db)
    }

    private fun seedDevices(db: SupportSQLiteDatabase) {

        db.execSQL("""
            INSERT INTO Device
            (Id, Name, pointValue, DisposalGuidance, IconName)
            VALUES
            (1, 'Smartphone', 30,
             'Back up your data, perform a factory reset, and remove the SIM card before recycling.',
             'smartphone'),

            (2, 'Charger', 10,
             'Unplug the charger and take it to an electronic waste collection point.',
             'charger'),

            (3, 'Tablet', 40,
             'Back up your data, remove personal information, and perform a factory reset.',
             'tablet'),

            (4, 'Laptop', 50,
             'Back up your files and securely erase personal data before recycling.',
             'laptop'),

            (5, 'Accessories', 5,
             'Collect electronic accessories and take them to an appropriate recycling point.',
             'accessories'),

            (6, 'Other Devices', 0,
             'Check whether your local recycling center accepts this device. Remove personal data where applicable and follow device-specific battery safety instructions before recycling.',
             'other')
        """.trimIndent())
    }

    private fun seedCollectionPoints(db: SupportSQLiteDatabase) {

        db.execSQL("""
            INSERT INTO CollectionPoint
            (Id, Name, Address, Latitude, Longitude)
            VALUES
            (1, 'Dammam Recycling Center (Demo)',
             'Dammam, Saudi Arabia', 26.4207, 50.0888),

            (2, 'Khobar E-Waste Center (Demo)',
             'Al Khobar, Saudi Arabia', 26.2172, 50.1971),

            (3, 'Dhahran Collection Point (Demo)',
             'Dhahran, Saudi Arabia', 26.2361, 50.0393),

            (4, 'Al Faisaliyah Recycling Point (Demo)',
             'Al Faisaliyah, Dammam', 26.3910, 50.0490),

            (5, 'Al Rakah Collection Point (Demo)',
             'Al Rakah, Al Khobar', 26.3070, 50.2030)
        """.trimIndent())
    }

    private fun seedBadges(db: SupportSQLiteDatabase) {

        db.execSQL("""
            INSERT INTO Badge
            (Id, title, requiredDevices, IconName)
            VALUES
            (1, 'First Loop', 1, 'first_loop'),
            (2, '5 Devices', 5, 'five_devices'),
            (3, '10 Devices', 10, 'ten_devices')
        """.trimIndent())
    }

    private fun seedChallenges(db: SupportSQLiteDatabase) {

        db.execSQL("""
            INSERT INTO Challenge
            (Id, Title, targetCount, bonusPoints)
            VALUES
            (1, 'Recycle 3 Devices', 3, 20),
            (2, 'Recycle 5 Devices', 5, 50),
            (3, 'Recycle 10 Devices', 10, 100)
        """.trimIndent())
    }

    private fun seedCollectionPointDevices(
        db: SupportSQLiteDatabase
    ) {

        db.execSQL("""
            INSERT INTO CollectionPointDevice
            (collectionPointId, deviceId)
            VALUES
            (1, 1),
            (1, 2),
            (1, 3),
            (1, 4),
            (1, 5),

            (2, 1),
            (2, 2),
            (2, 4),

            (3, 1),
            (3, 3),
            (3, 4),

            (4, 2),
            (4, 5),

            (5, 1),
            (5, 2),
            (5, 3),
            (5, 5)
        """.trimIndent())
    }
}
