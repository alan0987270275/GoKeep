package com.example.gokeep.data.localdb

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrationHelper {

    /**
     * Migrate version1 to version2. Create new TABLE `Spending`
     */

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `Spending` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`tag` TEXT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`cost` INTEGER NOT NULL, " +
                    "`createdTimeStamp` INTEGER NOT NULL)")
        }
    }

    /**
     * Migrate version2 to version3. Alter new COLUMN `month` for TABLE `Spending`
     */
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `Spending` " +
                    "ADD COLUMN month INTEGER NOT NULL DEFAULT 0")
        }
    }

}