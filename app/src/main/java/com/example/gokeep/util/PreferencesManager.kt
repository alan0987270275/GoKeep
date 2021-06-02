package com.example.gokeep.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {

    private lateinit var preferencesManager: SharedPreferences

    private const val PREF_NAME = "com.example.gokeep.PREFERENCE_FILE_KEY"

    private val validKeys: List<String> = listOf (
       "first_time_in_app"
    )


    fun init(context: Context) {
        preferencesManager = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    open fun get(key: String, defValue: Any): Any? {
        if (!validKeys.contains(key))
            return null
        return when(defValue) {
            is String -> preferencesManager.getString(key, defValue)
            is Int -> preferencesManager.getInt(key, defValue)
            is Boolean -> preferencesManager.getBoolean(key, defValue)
            is Long -> preferencesManager.getLong(key, defValue)
            is Float -> preferencesManager.getFloat(key, defValue)
            is Set<*> -> {
                if(defValue.isNotEmpty() && defValue.elementAt(0) is String)
                    preferencesManager.getStringSet(key, defValue as Set<String>)
                else
                    return null
            }
            else -> null
        }
    }

    open fun set(key: String, value: Any):Any? {
        if (!validKeys.contains(key))
            return null
        val prefsEditor: SharedPreferences.Editor = preferencesManager.edit()
        with(prefsEditor) {
            when(value){
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Set<*> -> {
                    if(value.isNotEmpty() && value.elementAt(0) is String)
                        putStringSet(key, value as Set<String>)
                    else
                        return null
                }
                else -> return null
            }
            commit()
        }
        return value
    }

}