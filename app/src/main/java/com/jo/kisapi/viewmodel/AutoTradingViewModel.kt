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

    val longStartPrice = MutableLiveData(0)
    val shortStartPrice = MutableLiveData(0)

    val longTimePrice = MutableLiveData(0)
    val shortTimePrice = MutableLiveData(0)

    var longMax = MutableLiveData<Int>()
    var shortMax = MutableLiveData<Int>()

    val longYDdPrice = MutableLiveData<Int>()
    val shortYDPrice = MutableLiveData<Int>()

    val cashes = MutableLiveData<Int>(0)

    val longCount = MutableLiveData<String>("1")
    val shortCount = MutableLiveData<String>("1")
    val dec = DecimalFormat("#,###원")

    fun getLongMaxPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(no).let {
                longMax.value = it.body()!!.prpr.stck_mxpr
            }

        }
    }
    fun getShortMaxPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(no).let {
                shortMax.value = it.body()!!.prpr.stck_mxpr
            }

        }
    }

    fun getLongTimePrice(no: String) {
        viewModelScope.launch {
            repository.getTimePrice(
                no
            ).let {
                Log.d("test", it.body()!!.chartPrice[0].stck_prpr.toString()) // 930분봉 종가
                longTimePrice.value = it.body()!!.chartPrice[0].stck_prpr
            }
        }
    }

    fun getShortTimePrice(no: String) {
        viewModelScope.launch {
            repository.getTimePrice(
                no
            ).let {
                Log.d("test", it.body()!!.chartPrice[0].stck_prpr.toString()) // 930분봉 종가
                shortTimePrice.value = it.body()!!.chartPrice[0].stck_prpr
            }
        }
    }

    fun getLongStarPrice(no: String) {
        viewModelScope.launch {
            repository.getDailyPrice(no).let {
                longStartPrice.value = it.body()!!.DailyPriceList[0].stck_oprc.toInt()  //당일 시가
                longYDdPrice.value = it.body()!!.DailyPriceList[1].stck_clpr.toInt()    //전일 종가
            }
        }
    }

    fun getShortStarPrice(no: String) {
        viewModelScope.launch {
            repository.getDailyPrice(no).let {
                shortStartPrice.value = it.body()!!.DailyPriceList[0].stck_oprc.toInt()  //당일 시가
                shortYDPrice.value = it.body()!!.DailyPriceList[1].stck_clpr.toInt()    //전일 종가
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