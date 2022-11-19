package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.usecase.GetCurrentPriceUseCase
import com.jo.kisapi.usecase.GetDailyPriceUseCase
import com.jo.kisapi.usecase.GetMyCashUseCase
import com.jo.kisapi.usecase.GetTimePriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AutoTradingViewModel @Inject constructor(
    private val getCurrentPriceUseCase: GetCurrentPriceUseCase,
    private val getTimePriceUseCase: GetTimePriceUseCase,
    private val getDailyPriceUseCase: GetDailyPriceUseCase,
    private val getMyCashUseCase: GetMyCashUseCase
) :
    ViewModel() {

    val longStartPrice = mutableStateOf<Int>(0)
    val shortStartPrice = mutableStateOf<Int>(0)

    val longTimePrice = mutableStateOf<Int>(0)
    val shortTimePrice = mutableStateOf<Int>(0)

    var longMax = mutableStateOf<Int>(0)
    var shortMax = mutableStateOf<Int>(0)

    val longYDPrice = mutableStateOf<Int>(0)
    val shortYDPrice = mutableStateOf<Int>(0)

    val cashes = mutableStateOf<Int>(0)

    val longCount = mutableStateOf<String>("1")
    val shortCount = mutableStateOf<String>("1")
    val dec = DecimalFormat("#,###원")

    fun getLongMaxPrice(no: String) {
        viewModelScope.launch {
            getCurrentPriceUseCase(no).collect {
                longMax.value = it.prpr.stck_mxpr
            }

        }
    }
    fun getShortMaxPrice(no: String) {
        viewModelScope.launch {
            getCurrentPriceUseCase(no).collect {
                shortMax.value = it.prpr.stck_mxpr
            }

        }
    }

    fun getLongTimePrice(no: String) {
        viewModelScope.launch {
            getTimePriceUseCase(
                no
            ).let {
                Log.d("test", it.chartPrice[0].stck_prpr.toString()) // 930분봉 종가
                longTimePrice.value = it.chartPrice[0].stck_prpr
            }
        }
    }

    fun getShortTimePrice(no: String) {
        viewModelScope.launch {
            getTimePriceUseCase(
                no
            ).let {
                Log.d("test", it.chartPrice[0].stck_prpr.toString()) // 930분봉 종가
                shortTimePrice.value = it.chartPrice[0].stck_prpr
            }
        }
    }

    fun getLongStarPrice(no: String) {
        viewModelScope.launch {
            getDailyPriceUseCase(no).let {
                longStartPrice.value = it.DailyPriceList[0].stck_oprc.toInt()  //당일 시가
                longYDPrice.value = it.DailyPriceList[1].stck_clpr.toInt()    //전일 종가
            }
        }
    }

    fun getShortStarPrice(no: String) {
        viewModelScope.launch {
            getDailyPriceUseCase(no).let {
                shortStartPrice.value = it.DailyPriceList[0].stck_oprc.toInt()  //당일 시가
                shortYDPrice.value = it.DailyPriceList[1].stck_clpr.toInt()    //전일 종가
            }
        }
    }

    fun getCash(){
        viewModelScope.launch {
            getMyCashUseCase().let {
                cashes.value = it.cashOutput.max_buy_amt
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