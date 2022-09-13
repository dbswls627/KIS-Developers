package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AutoTradingViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val startPrice = MutableLiveData(0)
    val timePrice = MutableLiveData(0)

    val cashes = MutableLiveData<Int>(0)

    val longCount = MutableLiveData<String>("1")
    val shortCount = MutableLiveData<String>("1")
    val dec = DecimalFormat("#,###원")

    fun getTimePrice(no: String) {
        viewModelScope.launch {
            repository.getTimePrice(
                no
            ).let {
                Log.d("test", it.body()!!.chartPrice[0].stck_prpr.toString()) // 930분봉 종가
                timePrice.value = it.body()!!.chartPrice[0].stck_prpr
            }
        }
    }

    fun getStarPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(
                no
            ).let {
                Log.d("test", it.body()!!.prpr.stck_oprc.toString())
                startPrice.value = it.body()!!.prpr.stck_oprc
            }
        }
    }

    fun getCash(){
        viewModelScope.launch {
            repository.getCash().let {
                cashes.value = it.body()!!.cashOutput.max_buy_amt
            }
        }
    }

    fun longCountPlus() {
        if (longCount.value.isNullOrEmpty()) {
            longCount.value = "1"
        } else {
            longCount.value = (longCount.value!!.toInt() + 1).toString()
        }
    }

    fun longCountMinus() {
        if (longCount.value.isNullOrEmpty()) {
            longCount.value = "1"
        } else if (longCount.value!!.toInt() > 1) {
            longCount.value = (longCount.value!!.toInt() - 1).toString()
        }
    }

    fun shortCountPlus() {
        if (shortCount.value.isNullOrEmpty()) {
            shortCount.value = "1"
        } else {
            shortCount.value = (shortCount.value!!.toInt() + 1).toString()
        }
    }

    fun shortCountMinus() {
        if (shortCount.value.isNullOrEmpty()) {
            shortCount.value = "1"
        } else if (shortCount.value!!.toInt() > 1) {
            shortCount.value = (shortCount.value!!.toInt() - 1).toString()
        }
    }

}