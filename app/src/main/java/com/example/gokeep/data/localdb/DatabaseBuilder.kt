package com.example.gokeep.data.localdb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

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


    fun getInstance(context: Context): AppDatabase {
        if(INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "com-example-gokeep"
        ).build()
}