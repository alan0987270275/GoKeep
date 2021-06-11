package com.example.gokeep.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.gokeep.R

object ColorHelper {

    fun getStaticColorMap(context: Context, tag: String): Int? {
        val staticTagMap = mapOf(
                "Income" to ContextCompat.getColor(context, R.color.greenLight),
                "Goal" to ContextCompat.getColor(context, R.color.bluePrimaryDark),
                "Grocery" to ContextCompat.getColor(context, R.color.orange),
                "Transport" to ContextCompat.getColor(context, R.color.bluePrimary),
                "Shopping" to ContextCompat.getColor(context, R.color.yellowLight),
                "Restaurant" to ContextCompat.getColor(context, R.color.bluePrimaryLight),
                "Billing" to ContextCompat.getColor(context, R.color.red)
        )
        return staticTagMap[tag]
    }

}