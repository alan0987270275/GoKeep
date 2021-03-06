package com.example.gokeep.data.localdb

import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.SpendingGroupByTag
import com.example.gokeep.data.model.StaticMonthlySumDataFromDB
import com.example.gokeep.data.model.StaticMonthlyTagData

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getGoal() = appDatabase.goalDao().getAll()

    override suspend fun insertGoal(goal: Goal) = appDatabase.goalDao().insert(goal)

    override suspend fun getSpending(): List<Spending> = appDatabase.spendingDao().getAll()

    override suspend fun getSpendingByTime(
        date1: Long,
        date2: Long
    ) = appDatabase.spendingDao().getItemByTimeStamp(date1, date2)

    override suspend fun getSpendingByTagAndTime(
        date1: Long,
        date2: Long
    ) = appDatabase.spendingDao().getItemByTagAndTimeStamp(date1, date2)

    override suspend fun getSpendingByTagAndMonth(month: Int): List<SpendingGroupByTag> =
        appDatabase.spendingDao().getItemByTagAndMonth(month)

    override suspend fun getStaticDataGroupByMonth(): List<StaticMonthlySumDataFromDB> =
        appDatabase.spendingDao().getStaticDataGroupByMonth()

    override suspend fun getStaticMonthlyTagData(month: Int): List<StaticMonthlyTagData> =
        appDatabase.spendingDao().getStaticMonthlyTagData(month)

    override suspend fun insertSpending(spending: Spending) =
        appDatabase.spendingDao().insert(spending)
}