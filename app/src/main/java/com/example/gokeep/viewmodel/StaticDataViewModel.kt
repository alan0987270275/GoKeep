package com.example.gokeep.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokeep.data.localdb.DatabaseHelper
import com.example.gokeep.data.model.StaticMonthlySumData
import com.example.gokeep.data.model.StaticMonthlySumDataFromDB
import com.example.gokeep.util.Resource
import kotlinx.coroutines.launch

class StaticDataViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val TAG = StaticDataViewModel::javaClass.name
    private val staticMonthlySumDataFromDB = MutableLiveData<Resource<ArrayList<StaticMonthlySumDataFromDB>>>()

    init {
        fetchStaticMonthlySumData()
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

    private fun <T> MutableLiveData<T>.notifyObserver() {
        // Make this call can using the background thread to change the LiveData,
        this.postValue(this.value)
        // Using setValue can only call in MainThread
//        this.value = this.value
    }

    fun getStaticMonthlySumDataFromDB() = staticMonthlySumDataFromDB
}