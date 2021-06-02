package com.example.gokeep.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gokeep.data.localdb.dao.GoalDao
import com.example.gokeep.data.localdb.dao.SpendingDao
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending

@Database(entities = [Goal::class, Spending::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun goalDao(): GoalDao
    abstract fun spendingDao(): SpendingDao
}