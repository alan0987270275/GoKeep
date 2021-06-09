package com.example.gokeep.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokeep.data.localdb.DatabaseHelper
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.SpendingGroupByTag
import com.example.gokeep.util.DateHelper.atEndOfDay
import com.example.gokeep.util.DateHelper.atStartOfDay
import com.example.gokeep.util.DateHelper.getIsTodayOrIsYesterday
import com.example.gokeep.util.Resource
import com.example.gokeep.util.dateCompare
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class RoomDBViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val TAG = RoomDBViewModel::javaClass.name
    private val goals = MutableLiveData<Resource<ArrayList<Goal>>>()
    private val todaySpending = MutableLiveData<Resource<ArrayList<SpendingGroupByTag>>>()
    private val yesterdaySpending = MutableLiveData<Resource<ArrayList<SpendingGroupByTag>>>()

    init {
        fetchGoals()

        // get today
        val calendar = Calendar.getInstance()
        fetchTodaySpending(date1 = atStartOfDay(calendar.time), date2 = atEndOfDay(calendar.time))

        //get yesterday
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        fetchYesterdaySpending(atStartOfDay(calendar.time), atEndOfDay(calendar.time))
    }

    private fun fetchGoals() {
        viewModelScope.launch {
            goals.postValue(Resource.loading(null))
            try {
                val goalFromDb = dbHelper.getGoal()
                if(goalFromDb.isNotEmpty()) {
                    goals.postValue(Resource.success(java.util.ArrayList(goalFromDb)))
                } else {
                    goals.postValue(Resource.success(java.util.ArrayList()))
                }
            } catch (e: Exception) {
                goals.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    private fun fetchTodaySpending(date1: Long, date2: Long) {
        viewModelScope.launch {
            todaySpending.postValue(Resource.loading(null))
            try {
                val spendingFromDBB = dbHelper.getSpendingByTagAndTime(date1, date2)
                if(spendingFromDBB.isNotEmpty()) {
                    todaySpending.postValue(Resource.success(java.util.ArrayList(spendingFromDBB)))
                } else {
                    todaySpending.postValue(Resource.success(java.util.ArrayList()))
                }
            } catch (e: Exception) {
                todaySpending.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    private fun fetchYesterdaySpending(date1: Long, date2: Long) {
        viewModelScope.launch {
            yesterdaySpending.postValue(Resource.loading(null))
            try {
                val spendingFromDBB = dbHelper.getSpendingByTagAndTime(date1, date2)
                if (spendingFromDBB.isNotEmpty()) {
                    yesterdaySpending.postValue(Resource.success(java.util.ArrayList(spendingFromDBB)))
                } else {
                    yesterdaySpending.postValue(Resource.success(java.util.ArrayList()))
                }
            } catch (e: Exception) {
                yesterdaySpending.postValue(Resource.error(e.toString(), null))
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
        val spendingGroupByTag = SpendingGroupByTag(
            spending.tag,
            spending.title,
            spending.cost,
            spending.createdTimeStamp
        )
        when(getIsTodayOrIsYesterday(spending.createdTimeStamp)) {
            dateCompare.ISTODAY -> {
                todaySpending.value?.data?.add(0, spendingGroupByTag)
                todaySpending.notifyObserver()
            }
            dateCompare.ISYESTER -> {
                yesterdaySpending.value?.data?.add(0, spendingGroupByTag)
                yesterdaySpending.notifyObserver()
            }
            dateCompare.ISOTHER -> {
            }
        }
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

    fun getTodaySpending(): LiveData<Resource<ArrayList<SpendingGroupByTag>>> {
        return todaySpending
    }

    fun getYesterdaySpending(): LiveData<Resource<ArrayList<SpendingGroupByTag>>> {
        return yesterdaySpending
    }
}