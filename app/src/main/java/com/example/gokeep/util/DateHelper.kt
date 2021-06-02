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

    private fun format(format: String, date: Date): String = SimpleDateFormat(format, Locale.TAIWAN).format(date)
}