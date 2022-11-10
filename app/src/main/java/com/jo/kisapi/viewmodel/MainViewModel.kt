package com.jo.kisapi.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getTokenTimeUseCase: GetTokenTimeUseCase,
    private val getTradingDBHistoryUseCase: GetTradingDBHistoryUseCase,
    private val getTradingHistoryUseCase: GetTradingHistoryUseCase,
    private val getBuySumUseCase: GetBuySumUseCase,
    private val getSellSumUseCase: GetSellSumUseCase,
    private val insertTokenUseCase: InsertTokenUseCase,
    private val insertTradingHistoryUseCase: InsertTradingHistoryUseCase,
) : ViewModel() {

    val msg = MutableLiveData<String>()
    val aChange = MutableLiveData<Int>()
    val bChange = MutableLiveData<Int>()

    private lateinit var aBuyList: ArrayList<String>
    private lateinit var bBuyList: ArrayList<String>

    private lateinit var aSellList: ArrayList<String>
    private lateinit var bSellList: ArrayList<String>

    private fun getToken() {
        viewModelScope.launch {
            try {
                getTokenUseCase()
                    .let {
                        insertTokenUseCase(
                            TokenTime(
                                "Bearer",
                                "Bearer " + it.access_token,
                                System.currentTimeMillis().plus(80000000).toString()
                            )
                        )
                    }
            } catch (e: Exception) {

            }
        }
    }

    fun getTokenCheck() {
        viewModelScope.launch {
            try {
                getTokenTimeUseCase().let {
                    if (it.toLong() < System.currentTimeMillis()) {
                        getToken()
                    }
                }
            } catch (e: NumberFormatException) {
                getToken()   //저장된 토큰이 없을 때
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun changeRefresh() {
        viewModelScope.launch {

            setChange()
            getList()
            Log.d("test", aBuyList.isEmpty().toString())

            //db의 저장되지 않은 값이 있으면 손익 계산
            if (aSellList.isNotEmpty() || bSellList.isNotEmpty()) {
                getTradingHistory()

                setChange()
            }
        }
    }

    private suspend fun getList() {

        aBuyList = getTradingDBHistoryUseCase("A", "01") as ArrayList<String>
        aSellList = getTradingDBHistoryUseCase("A", "02") as ArrayList<String>

        bBuyList = getTradingDBHistoryUseCase("B", "01") as ArrayList<String>
        bSellList = getTradingDBHistoryUseCase("B", "02") as ArrayList<String>


    }

    // 누적 손익값
    private suspend fun setChange() {
        aChange.value = getSellSumUseCase("A") - getBuySumUseCase("A")
        bChange.value = getSellSumUseCase("B") - getBuySumUseCase("B")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getTradingHistory() {

        val endDay: LocalDate = LocalDate.now()
        val startDay = endDay.minusMonths(1)

        getTradingHistoryUseCase(
            startDay.toString().replace("-", ""),
            endDay.toString().replace("-", ""),
            "01"//매도
        ).tradingHistoryList.forEach {

            if (aSellList.contains(it.odno)) {//등록이 되지않은 매도기록
                insertTradingHistoryUseCase(
                    AutoTrading(
                        "A",
                        "02",
                        it.odno,
                        it.tot_ccld_amt.toInt()
                    )
                )
                getTradingHistoryUseCase(it.ord_dt, it.ord_dt, "02")!!//매도날짜 당일 매수기록
                    .tradingHistoryList.forEach {
                        if (aBuyList.contains(it.odno)) {
                            insertTradingHistoryUseCase(
                                AutoTrading(
                                    "A",
                                    "01",
                                    it.odno,
                                    it.tot_ccld_amt.toInt()
                                )
                            )
                        }
                    }
            }

            if (bSellList.contains(it.odno)) {
                insertTradingHistoryUseCase(
                    AutoTrading(
                        "B",
                        "02",
                        it.odno,
                        it.tot_ccld_amt.toInt()
                    )
                )
                getTradingHistoryUseCase(it.ord_dt, it.ord_dt, "02")!!
                    .tradingHistoryList.forEach {
                        if (bBuyList.contains(it.odno)) {
                            insertTradingHistoryUseCase(
                                AutoTrading(
                                    "B",
                                    "01",
                                    it.odno,
                                    it.tot_ccld_amt.toInt()
                                )
                            )
                        }
                    }
            }


        }
    }
}

