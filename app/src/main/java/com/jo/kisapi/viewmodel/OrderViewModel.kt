package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: Repository) : ViewModel() {

    val currentPrice = MutableLiveData<Int>(0)
    val targetPrice = MutableLiveData<Int>(0)

    fun getCurrentPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                currentPrice.value = it.body()!!.prpr.stck_prpr
                Log.d("test", it.body()!!.prpr.prdy_ctrt.toString())
            }

        }
    }

    fun getTargetPrice(no: String) {
        viewModelScope.launch {
            repository.getDailyPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                targetPrice.value = (it.body()!!.DailyPriceList[0].stck_oprc.toInt() +
                        (it.body()!!.DailyPriceList[1].stck_hgpr.toInt() - it.body()!!.DailyPriceList[1].stck_lwpr.toInt()) * 0.5).toInt()

            }
        }
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
                return OrderViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}