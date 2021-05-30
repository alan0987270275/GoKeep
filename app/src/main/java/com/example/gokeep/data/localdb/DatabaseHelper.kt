package com.example.gokeep.data.localdb

import com.example.gokeep.data.localdb.entity.Goal

interface DatabaseHelper {

    suspend fun getGoal(): List<Goal>

    suspend fun insertGoal(goal: Goal)

}