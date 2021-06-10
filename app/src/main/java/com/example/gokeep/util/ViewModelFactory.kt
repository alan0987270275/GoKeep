package com.example.gokeep.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gokeep.viewmodel.SpendingViewModel
import com.example.gokeep.data.localdb.DatabaseHelper
import com.example.gokeep.viewmodel.StaticDataViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SpendingViewModel::class.java)) {
            return SpendingViewModel(dbHelper) as T
        }
        if (modelClass.isAssignableFrom(StaticDataViewModel::class.java)) {
            return StaticDataViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}