package com.example.gokeep.data.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gokeep.data.localdb.entity.Spending

@Dao
interface SpendingDao {

    @Query("SELECT * FROM Spending ORDER BY id DESC")
    suspend fun getAll(): List<Spending>

    @Insert
    suspend fun insert(spending: Spending)

    @Delete
    suspend fun delete(spending: Spending)
}