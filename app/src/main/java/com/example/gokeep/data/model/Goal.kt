package com.example.gokeep.data.model


data class Goal(
    val title: String,
    val imageUrl: String,
    val progress: Int,
    val validTimeStamp: Long,
    val createdTimestamp: Long
)


