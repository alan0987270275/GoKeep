package com.example.gokeep.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokeep.data.localdb.DatabaseHelper
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.util.Resource
import kotlinx.coroutines.launch

class RoomDBViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val goals = MutableLiveData<Resource<ArrayList<Goal>>>()
    private val spendings = MutableLiveData<Resource<ArrayList<Spending>>>()

    init {
        fetchGoals()
        fetchSpending()
    }

    private fun fetchGoals() {
        viewModelScope.launch {
            goals.postValue(Resource.loading(null))
            try {
                val goalFromDb = dbHelper.getGoal()
                if(goalFromDb.isNotEmpty()) {
                    goals.postValue(Resource.success(java.util.ArrayList(goalFromDb)))
                }
            } catch (e: Exception) {
                goals.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    private fun fetchSpending() {
        viewModelScope.launch {
            spendings.postValue(Resource.loading(null))
            try {
                val spendingFromDBB = dbHelper.getSpending()
                if(spendingFromDBB.isNotEmpty()) {
                    spendings.postValue(Resource.success(java.util.ArrayList(spendingFromDBB)))
                }
            } catch (e: Exception) {
                spendings.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun insertGaol(goal: Goal) = viewModelScope.launch {
        dbHelper.insertGoal(goal)
        // add new element to first place
        goals.value?.data?.add(0, goal)
        goals.notifyObserver()
    }

    fun insertSpending(spending: Spending) = viewModelScope.launch {
        dbHelper.insertSpending(spending)
        spendings.value?.data?.add(0, spending)
        spendings.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        // Make this call can using the background thread to change the LiveData,
        this.postValue(this.value)
        // Using setValue can only call in MainThread
//        this.value = this.value
    }

    fun getGoals(): LiveData<Resource<ArrayList<Goal>>> {
        return goals
    }

    fun getSpendings(): LiveData<Resource<ArrayList<Spending>>> {
        return spendings
    }
}