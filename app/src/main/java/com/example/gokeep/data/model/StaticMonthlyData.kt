package com.example.gokeep.data.model

import com.example.gokeep.util.DateHelper

data class StaticMonthlySumDataFromDB(
    val sumSpending: Int = 0,
    val _monthTitle: Int
) {
    val monthTitle: String?
        get() = DateHelper.monthMap[_monthTitle]
}

data class StaticMonthlySumData(
    val sumSpending: Int = 0,
    var monthTitle: String?,
    var isSelected: Boolean = false
)

data class StaticMonthlyTagData(
    val sumSpending: Int = 0,
    var monthTitle: String?,
    val tag: String
)
