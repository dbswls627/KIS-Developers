package com.jo.kisapi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoTrading1ViewModel @Inject constructor(
    private val getCurrentPriceUseCase: GetCurrentPriceUseCase,
    private val getDailyPriceUseCase: GetDailyPriceUseCase,
    private val getMyCashUseCase: GetMyCashUseCase,
    private val insertTradingHistoryUseCase: InsertTradingHistoryUseCase,
    private val postOrderBuyUseCase: PostOrderBuyUseCase,
    private val postOrderSellUseCase: PostOrderSellUseCase,
) : ViewModel() {

    val longCount = mutableStateOf<String>("1")
    val shortCount = mutableStateOf<String>("1")

    val longCurrentPrice = MutableStateFlow<Int>(0)
    val shortCurrentPrice = MutableStateFlow<Int>(0)

    var longMax = mutableStateOf<Int>(0)
    var shortMax = mutableStateOf<Int>(0)

    val longTargetPrice = mutableStateOf<Int>(0)
    val shortTargetPrice = mutableStateOf<Int>(0)

    val longYDPrice = mutableStateOf<Int>(0)
    val shortYDPrice = mutableStateOf<Int>(0)

    val cashes = mutableStateOf<Int>(0)
    val msg = mutableStateOf<String>("")

    val auto = mutableStateOf(false)
    val dec = mutableStateOf("#,###원")

    fun getLongCurrentPrice(no: String) {
        viewModelScope.launch {
            getCurrentPriceUseCase(no).collect {
                longCurrentPrice.value = it.prpr.stck_prpr
                longMax.value = it.prpr.stck_mxpr
            }

        }
    }

    fun getShortCurrentPrice(no: String) {
        viewModelScope.launch {
            getCurrentPriceUseCase(no).collect {
                shortCurrentPrice.value = it.prpr.stck_prpr
                shortMax.value = it.prpr.stck_mxpr
            }

        }
    }

    fun getLongTargetPrice(no: String) {
        viewModelScope.launch {
            getDailyPriceUseCase(no).let {
                longTargetPrice.value = (it.DailyPriceList[0].stck_oprc.toInt() +
                        (it.DailyPriceList[1].stck_hgpr.toInt() - it.DailyPriceList[1].stck_lwpr.toInt()) * 0.5).toInt()
                longYDPrice.value = it.DailyPriceList[1].stck_clpr.toInt()
            }
        }
    }

    fun getShortTargetPrice(no: String) {
        viewModelScope.launch {
            getDailyPriceUseCase(no).let {
                shortTargetPrice.value = (it.DailyPriceList[0].stck_oprc.toInt() +
                        (it.DailyPriceList[1].stck_hgpr.toInt() - it.DailyPriceList[1].stck_lwpr.toInt()) * 0.5).toInt()
                shortYDPrice.value = it.DailyPriceList[1].stck_clpr.toInt()
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
            getMyCashUseCase().let {
                cashes.value = it.cashOutput.max_buy_amt
            }
        }
    }

    private fun orderSell(pdno:String, count :String) {
       var amt = ""
        viewModelScope.launch {
            if (pdno == "069500") {
                amt = longMax.value.toString()
            }else{
                amt = shortMax.value.toString()
            }
            postOrderSellUseCase(
                pdno,
                "02",
                count,
                amt
            ).let {
                insertTradingHistoryUseCase(AutoTrading("A", "02", it.output.ODNO,0))

            }
        }

    }



    fun longAuto(no: String) {

            viewModelScope.launch {
                while (auto.value!!) {

                    if (longTargetPrice.value!! <= longCurrentPrice.value!!.toInt()) {

                        postOrderBuyUseCase(
                            no,
                            "01",
                            longCount!!.value.toString(),
                            "0"
                        ).let {
                            try {

                                msg.value = it.msg1
                                insertTradingHistoryUseCase(AutoTrading("A", "01", it.output.ODNO,0))
                                auto.value = false
                                getCash()
                                orderSell(no, longCount.value.toString())

                            } catch (e: Exception) {
                                auto.value = true
                            }

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
                    postOrderBuyUseCase(
                        no,
                        "01",
                        shortCount!!.value.toString(),
                        "0"
                    ).let {
                        try {

                            auto.value = false
                            insertTradingHistoryUseCase(AutoTrading("A", "01", it.output.ODNO,0))
                            msg.value = it.msg1
                            orderSell(no, shortCount.value.toString())
                        } catch (e: Exception) {
                            auto.value = true
                        }

                    }
                }
                delay(1000)
            }
        }

    }
}