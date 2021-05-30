package com.example.gokeep.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokeep.data.localdb.DatabaseHelper
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.util.Resource
import kotlinx.coroutines.launch

class RoomDBViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val goals = MutableLiveData<Resource<List<Goal>>>()

    init {
        fetchGoals()
    }

    private fun fetchGoals() {
        viewModelScope.launch {
            goals.postValue(Resource.loading(null))
            try {
                val goalFromDb = dbHelper.getGoal()
                if(goalFromDb.isNotEmpty()) {
                    goals.postValue(Resource.success(goalFromDb))
                }
            } catch (e: Exception) {
                goals.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }
}