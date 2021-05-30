package com.example.gokeep.data.localdb

import com.example.gokeep.data.localdb.entity.Goal

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getGoal() = appDatabase.goalDao().getAll()

    override suspend fun insertGoal(goal: Goal) = appDatabase.goalDao().insert(goal)
}