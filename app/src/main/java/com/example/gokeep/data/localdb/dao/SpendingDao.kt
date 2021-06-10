package com.example.gokeep.data.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.SpendingGroupByTag
import com.example.gokeep.data.model.StaticMonthlySumDataFromDB
import com.example.gokeep.data.model.StaticMonthlyTagData

@Dao
interface SpendingDao {

    @Query("SELECT * FROM Spending ORDER BY id DESC")
    suspend fun getAll(): List<Spending>

    @Query("SELECT * FROM Spending WHERE createdTimeStamp BETWEEN :date1 AND :date2 ORDER BY id DESC")
    suspend fun getItemByTimeStamp(date1: Long, date2: Long): List<Spending>

    @Query(
        """
        SELECT tag, group_concat(title, ', ') AS concatTitle, sum(cost) AS sumCost, createdTimeStamp FROM Spending 
        WHERE createdTimeStamp BETWEEN :date1 AND :date2 GROUP BY tag ORDER BY id DESC
        """
    )
    suspend fun getItemByTagAndTimeStamp(date1: Long, date2: Long): List<SpendingGroupByTag>

    @Query("SELECT SUM(cost) as sumSpending, month as _monthTitle FROM Spending GROUP BY month")
    suspend fun getStaticDataGroupByMonth(): List<StaticMonthlySumDataFromDB>

    @Query(
        """
        SELECT SUM(cost) as sumSpending, month as monthTitle, tag FROM Spending
        WHERE month = :month GROUP BY tag
        """
    )
    suspend fun getStaticMonthlyTagData(month: Int): List<StaticMonthlyTagData>

    @Insert
    suspend fun insert(spending: Spending)

    @Delete
    suspend fun delete(spending: Spending)
}