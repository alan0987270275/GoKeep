package com.example.gokeep.util

import android.app.DatePickerDialog
import android.view.View
import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun onDateSetListener(view: View, selectedCalendar: Calendar) = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        selectedCalendar.set(year, monthOfYear, dayOfMonth)
        (view as Button).text = format("yyyy / MM / dd", selectedCalendar.time)
    }

    fun atEndOfDay(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.timeInMillis
    }

    fun atStartOfDay(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }


    private fun format(format: String, date: Date): String = SimpleDateFormat(format, Locale.TAIWAN).format(date)
}