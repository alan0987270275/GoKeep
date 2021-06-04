package com.example.gokeep.data.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.SpendingGroupByTag

@Dao
interface SpendingDao {

    @Query("SELECT * FROM Spending ORDER BY id DESC")
    suspend fun getAll(): List<Spending>

    @Query("SELECT * FROM Spending WHERE createdTimeStamp BETWEEN :date1 AND :date2 ORDER BY id DESC")
    suspend fun getItemByTimeStamp(date1: Long, date2: Long): List<Spending>

    @Query("""
        SELECT tag, group_concat(title, ', ') AS concatTitle, sum(cost) AS sumCost, createdTimeStamp FROM Spending 
        WHERE createdTimeStamp BETWEEN :date1 AND :date2 GROUP BY tag ORDER BY id DESC
        """)
    suspend fun getItemByTagAndTimeStamp(date1: Long, date2: Long): List<SpendingGroupByTag>

    @Insert
    suspend fun insert(spending: Spending)

    @Delete
    suspend fun delete(spending: Spending)
}