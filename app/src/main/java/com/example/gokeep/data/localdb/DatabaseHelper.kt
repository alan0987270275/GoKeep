package com.example.gokeep.data.localdb

import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending

interface DatabaseHelper {

    suspend fun getGoal(): List<Goal>

    suspend fun insertGoal(goal: Goal)

    suspend fun getSpending(): List<Spending>

    suspend fun getSpendingByTime(date1: Long, date2: Long): List<Spending>

    suspend fun insertSpending(spending: Spending)

}