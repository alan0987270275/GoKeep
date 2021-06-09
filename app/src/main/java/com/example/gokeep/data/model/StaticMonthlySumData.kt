package com.example.gokeep.data.model

import com.example.gokeep.util.DateHelper

data class StaticMonthlySumData (
    val spending: Int = 0,
    val _monthTitle: Int,
    var isSelected: Boolean
) {
    val monthTitle: String?
        get() = DateHelper.monthMap[_monthTitle]
}