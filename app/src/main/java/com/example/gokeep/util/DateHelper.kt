package com.example.gokeep.util

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*

enum class dateCompare{
    ISTODAY,
    ISYESTER,
    ISOTHER
}
object DateHelper {

    val monthMap = mapOf(
        Calendar.JANUARY to "Jan", Calendar.FEBRUARY to "Feb",
        Calendar.MARCH to "Mar", Calendar.APRIL to "Apr",
        Calendar.MAY to "May", Calendar.JUNE to "Jun",
        Calendar.JULY to "Jul", Calendar.AUGUST to "Aug",
        Calendar.SEPTEMBER to "Sep", Calendar.OCTOBER to "Oct",
        Calendar.NOVEMBER to "Nov", Calendar.DECEMBER to "Dec"
    )

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

    // today -> 0, yesterday -> 1, else -> -1
    fun getIsTodayOrIsYesterday(timeStamp: Long):dateCompare {
        val calendar = Calendar.getInstance()
        var startTimeStamp = atStartOfDay(calendar.time)
        var endTimeStamp = atEndOfDay(calendar.time)
        if(timeStamp in startTimeStamp until endTimeStamp) return dateCompare.ISTODAY

        calendar.add(Calendar.DAY_OF_MONTH, -1)
        startTimeStamp = atStartOfDay(calendar.time)
        endTimeStamp = atEndOfDay(calendar.time)
        if(timeStamp in startTimeStamp until endTimeStamp) return dateCompare.ISYESTER

        return dateCompare.ISOTHER
    }


    fun format(format: String, date: Date): String = SimpleDateFormat(format, Locale.TAIWAN).format(date)
}