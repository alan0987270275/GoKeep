package com.example.gokeep.util

import java.text.DecimalFormat
import java.text.NumberFormat

object TextFormatHelper {

    fun moneyFormat(number: Any): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        return "$${formatter.format(number)}"
    }
}