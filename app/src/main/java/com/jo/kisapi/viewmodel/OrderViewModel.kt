package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.Util
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

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
    val auto = MutableLiveData(false)
    val dec = DecimalFormat("#,###원")

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
        if (count.value.isNullOrEmpty()) {
            count.value = "1"
        } else {
            count.value = (count.value!!.toInt() + 1).toString()
        }
    }

    fun countMinus() {
        if (count.value.isNullOrEmpty()) {
            count.value = "1"
        } else if (count.value!!.toInt() > 1) {
            count.value = (count.value!!.toInt() - 1).toString()
        }
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

   /* fun orderBuy(pdno:String, count :String) {
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
    }*/
    fun orderSell(pdno:String, count :String) {
        viewModelScope.launch {
            repository.order(
                "Bearer " + repository.dbToken(),
                Util.sell,
                pdno,
                "02",
                count,
                (targetPrice.value!! * 1.20).toString()
            ).let {

            }
        }

    }

    fun auto(no: String) {
        viewModelScope.launch {
            while (auto.value!!) {
                Log.d("test",currentPrice.value.toString())
                Log.d("test",targetPrice.value.toString())
                if (targetPrice.value!! <= currentPrice.value!!.toInt()) {
                    repository.order(
                        "Bearer " + repository.dbToken(),
                        Util.buy,
                        no,
                        "01",
                        count!!.value.toString()
                            ,"0"
                    ).let {
                        Log.d("test",it.body()!!.toString())
                        msg.value = it.body()!!.msg1
                        auto.value = false
                        repository.insert(AutoTrading("A","01",it.body()!!.output.ODNO))
                        orderSell(no,count.value.toString())
                    }
                }
                delay(1000)
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