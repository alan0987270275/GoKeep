package com.example.gokeep.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gokeep.data.localdb.dao.GoalDao
import com.example.gokeep.data.localdb.entity.Goal

@Database(entities = [Goal::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun goalDao(): GoalDao
}