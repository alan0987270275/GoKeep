package com.example.gokeep.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokeep.data.localdb.DatabaseHelper
import com.example.gokeep.data.model.StaticMonthlySumDataFromDB
import com.example.gokeep.data.model.StaticMonthlyTagData
import com.example.gokeep.util.Resource
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class StaticDataViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val TAG = StaticDataViewModel::javaClass.name
    private val staticMonthlySumDataFromDB = MutableLiveData<Resource<ArrayList<StaticMonthlySumDataFromDB>>>()
    private val staticMonthlyTagData = MutableLiveData<Resource<ArrayList<StaticMonthlyTagData>>>()

    init {
        val calendar = Calendar.getInstance()
        fetchStaticMonthlySumData()
        fetchStaticMonthlyTagData(calendar.get(Calendar.MONTH))
    }

    private fun fetchStaticMonthlySumData() {
        viewModelScope.launch {
            staticMonthlySumDataFromDB.postValue(Resource.loading(null))
            try {
                val dataFromDb = dbHelper.getStaticDataGroupByMonth()
                if(dataFromDb.isNotEmpty()) {
                    staticMonthlySumDataFromDB.postValue(Resource.success(java.util.ArrayList(dataFromDb)))
                } else {
                    staticMonthlySumDataFromDB.postValue(Resource.success(java.util.ArrayList()))
                }
            } catch (e: Exception) {
                staticMonthlySumDataFromDB.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun fetchStaticMonthlyTagData(month: Int) {
        viewModelScope.launch {
            staticMonthlyTagData.postValue(Resource.loading(null))
            try {
                val dataFromDb = dbHelper.getStaticMonthlyTagData(month)
                if(dataFromDb.isNotEmpty()) {
                    staticMonthlyTagData.postValue(Resource.success(java.util.ArrayList(dataFromDb)))
                } else {
                    staticMonthlyTagData.postValue(Resource.success(java.util.ArrayList()))
                }
            } catch (e: Exception) {
                staticMonthlyTagData.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        // Make this call can using the background thread to change the LiveData,
        this.postValue(this.value)
        // Using setValue can only call in MainThread
//        this.value = this.value
    }

    fun getStaticMonthlySumDataFromDB() = staticMonthlySumDataFromDB

    fun getStaticMonthlyTagData() = staticMonthlyTagData
}