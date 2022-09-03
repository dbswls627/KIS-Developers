package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.Util
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: Repository) : ViewModel() {
    val count = MutableLiveData<String>("1")
    val currentPrice = MutableLiveData<Int>()
    val targetPrice = MutableLiveData<Int>()
    val change = MutableLiveData<Int>()
    val percent = MutableLiveData<Double>()
    val ydPrice = MutableLiveData<Int>()
    val cashes = MutableLiveData<Int>(0)
    val msg = MutableLiveData<String>()
    val rt_cd = MutableLiveData<String>()

    fun getCurrentPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                currentPrice.value = it.body()!!.prpr.stck_prpr
                change.value = it.body()!!.prpr.prdy_vrss
                percent.value = it.body()!!.prpr.prdy_ctrt
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
                        (it.body()!!.DailyPriceList[1].stck_hgpr.toInt() - it.body()!!.DailyPriceList[1].stck_lwpr.toInt()) * 0.4).toInt()
                ydPrice.value = it.body()!!.DailyPriceList[1].stck_clpr.toInt()
                Log.d("test", (targetPrice.value!! / ydPrice.value!!.toDouble()).toString())
            }
        }
    }

    fun countPlus() {
        count.value = (count.value!!.toInt() + 1).toString()
    }

    fun countMinus() {
        count.value = (count.value!!.toInt() - 1).toString()
    }

    fun getCash(){
        viewModelScope.launch {
            repository.getCash(
                "Bearer " + repository.dbToken()
            ).let {
                cashes.value = it.body()!!.cashOutput.max_buy_amt
            }
        }
    }

    fun orderBuy(pdno:String, count :String) {
        viewModelScope.launch {
            repository.order(
                "Bearer " + repository.dbToken(),
                Util.buy,
                pdno,
                count
            ).let {
                msg.value = it.body()!!.msg1
                rt_cd.value = it.body()!!.rt_cd
                it.body()?.output?.odno.let { odno ->

                }
            }
        }
    }
    fun orderSell(pdno:String, count :String) {
        viewModelScope.launch {
            repository.order(
                "Bearer " + repository.dbToken(),
                Util.sell,
                pdno,
                count
            ).let {

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