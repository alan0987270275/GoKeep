package com.example.gokeep.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Spending (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "cost") val cost: Int,
    @ColumnInfo(name = "createdTimeStamp") val createdTimeStamp: Long,
    @ColumnInfo(name = "month") val month: Int
)