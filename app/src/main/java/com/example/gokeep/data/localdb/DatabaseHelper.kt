package com.example.gokeep.data.localdb

import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.SpendingGroupByTag
import com.example.gokeep.data.model.StaticMonthlySumDataFromDB
import com.example.gokeep.data.model.StaticMonthlyTagData

interface DatabaseHelper {

    suspend fun getGoal(): List<Goal>

    suspend fun insertGoal(goal: Goal)

    suspend fun getSpending(): List<Spending>

    suspend fun getSpendingByTime(date1: Long, date2: Long): List<Spending>

    suspend fun getSpendingByTagAndTime(date1: Long, date2: Long): List<SpendingGroupByTag>

    suspend fun getSpendingByTagAndMonth(month: Int): List<SpendingGroupByTag>

    suspend fun getStaticDataGroupByMonth(): List<StaticMonthlySumDataFromDB>

    suspend fun getStaticMonthlyTagData(month: Int): List<StaticMonthlyTagData>

    suspend fun insertSpending(spending: Spending)

}