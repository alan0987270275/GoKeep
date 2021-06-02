package com.example.gokeep.data.localdb

import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getGoal() = appDatabase.goalDao().getAll()

    override suspend fun insertGoal(goal: Goal) = appDatabase.goalDao().insert(goal)

    override suspend fun getSpending(): List<Spending> = appDatabase.spendingDao().getAll()

    override suspend fun insertSpending(spending: Spending) = appDatabase.spendingDao().insert(spending)
}