package com.example.gokeep.data.model


data class Goal(
    val title: String,
    val imageUrl: String,
    val budget: Int,
    val currentSaving: Int,
    val progress: Int,
    val validTimeStamp: Long,
    val createdTimestamp: Long
)


