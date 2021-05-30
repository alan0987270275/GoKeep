package com.example.gokeep.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "budget") val budget: Int,
    @ColumnInfo(name = "currentSaving") val currentSaving: Int,
    @ColumnInfo(name = "validTimeStamp") val validTimestamp: Long,
    @ColumnInfo(name = "createdTimeStamp") val createdTimeStamp: Long
)

