package com.example.gokeep.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gokeep.viewmodel.RoomDBViewModel
import com.example.gokeep.data.localdb.DatabaseHelper

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RoomDBViewModel::class.java)) {
            return RoomDBViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}