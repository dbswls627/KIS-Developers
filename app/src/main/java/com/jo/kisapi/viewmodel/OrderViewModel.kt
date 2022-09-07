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
import java.lang.Exception
import java.text.DecimalFormat

class OrderViewModel(private val repository: Repository) : ViewModel() {
    val longCount = MutableLiveData<String>("1")
    val shortCount = MutableLiveData<String>("1")

    val longCurrentPrice = MutableLiveData<Int>()
    val shortCurrentPrice = MutableLiveData<Int>()

    val longTargetPrice = MutableLiveData<Int>()
    val shortTargetPrice = MutableLiveData<Int>()

    val longChange = MutableLiveData<Int>()
    val shortChange = MutableLiveData<Int>()

    val longPercent = MutableLiveData<Double>()
    val shortPercent = MutableLiveData<Double>()

    val longYDdPrice = MutableLiveData<Int>()
    val shortYDPrice = MutableLiveData<Int>()

    val cashes = MutableLiveData<Int>(0)
    val msg = MutableLiveData<String>()
    val rt_cd = MutableLiveData<String>()
    val auto = MutableLiveData(false)
    val dec = DecimalFormat("#,###원")

    fun getLongCurrentPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                longCurrentPrice.value = it.body()!!.prpr.stck_prpr
                longChange.value = it.body()!!.prpr.prdy_vrss
                longPercent.value = it.body()!!.prpr.prdy_ctrt
            }

        }
    }

    fun getShortCurrentPrice(no: String) {
        viewModelScope.launch {
            repository.getCurrentPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                shortCurrentPrice.value = it.body()!!.prpr.stck_prpr
                shortChange.value = it.body()!!.prpr.prdy_vrss
                shortPercent.value = it.body()!!.prpr.prdy_ctrt
            }

        }
    }

    fun getLongTargetPrice(no: String) {
        viewModelScope.launch {
            repository.getDailyPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                longTargetPrice.value = (it.body()!!.DailyPriceList[0].stck_oprc.toInt() +
                        (it.body()!!.DailyPriceList[1].stck_hgpr.toInt() - it.body()!!.DailyPriceList[1].stck_lwpr.toInt()) * 0.4).toInt()
                longYDdPrice.value = it.body()!!.DailyPriceList[1].stck_clpr.toInt()
                Log.d("test", (longTargetPrice.value!! / longYDdPrice.value!!.toDouble()).toString())
            }
        }
    }

    fun getShortTargetPrice(no: String) {
        viewModelScope.launch {
            repository.getDailyPrice(
                "Bearer " + repository.dbToken(),
                no
            ).let {
                shortTargetPrice.value = (it.body()!!.DailyPriceList[0].stck_oprc.toInt() +
                        (it.body()!!.DailyPriceList[1].stck_hgpr.toInt() - it.body()!!.DailyPriceList[1].stck_lwpr.toInt()) * 0.4).toInt()
                shortYDPrice.value = it.body()!!.DailyPriceList[1].stck_clpr.toInt()
                Log.d("test", (shortTargetPrice.value!! / shortYDPrice.value!!.toDouble()).toString())
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
                (longTargetPrice.value!! * 1.20).toString()
            ).let {

            }
        }

    }

    fun longAuto(no: String) {

            viewModelScope.launch {
                while (auto.value!!) {

                    if (longTargetPrice.value!! <= longCurrentPrice.value!!.toInt()) {
                        repository.order(
                            "Bearer " + repository.dbToken(),
                            Util.buy,
                            no,
                            "01",
                            longCount!!.value.toString(), "0"
                        ).let {
                            try {
                                Log.d("test", it.body()!!.toString())
                                msg.value = it.body()!!.msg1
                                auto.value = false
                                repository.insert(AutoTrading("A", "01", it.body()!!.output.ODNO))
                                orderSell(no, longCount.value.toString())
                            }catch (e:Exception){}
                            auto.value = false
                            Log.d("TEst","ted")
                        }
                    }
                    delay(1000)
                }
            }

    }

    fun shortAuto(no: String) {

        viewModelScope.launch {
            while (auto.value!!) {

                if (shortTargetPrice.value!! <= shortCurrentPrice.value!!.toInt()) {
                    repository.order(
                        "Bearer " + repository.dbToken(),
                        Util.buy,
                        no,
                        "01",
                        shortCount!!.value.toString(), "0"
                    ).let {
                        try {
                            Log.d("test", it.body()!!.toString())
                            auto.value = false
                            repository.insert(AutoTrading("A", "01", it.body()!!.output.ODNO))
                            msg.value = it.body()!!.msg1
                            orderSell(no, shortCount.value.toString())
                        }catch (e:Exception){}
                        auto.value = true
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