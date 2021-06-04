package com.example.gokeep.data.model



data class SpendingGroupByTag (
    val tag: String,
    var concatTitle: String,
    var sumCost: Int,
    val createdTimeStamp: Long
)