package com.example.gokeep.data.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gokeep.data.localdb.entity.Goal

@Dao
interface GoalDao {

    @Query("SELECT * FROM Goal ORDER BY id DESC")
    suspend fun getAll(): List<Goal>

    @Insert
    suspend fun insert(goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)
}