package com.jo.kisapi.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

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
                repository.getToken()
                    .let {
                        repository.insert(
                            TokenTime(
                                "Bearer",
                                "Bearer " + it.body()!!.access_token,
                                System.currentTimeMillis().plus(80000000).toString()
                            )
                        )
                    }
            }catch (e:Exception){

            }
        }
    }

    fun getTokenCheck() {
        viewModelScope.launch{
            try {
                repository.getTime().let {
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
            if ( aBuyList.isNotEmpty() || bBuyList.isNotEmpty() ) {
                getTradingHistory()
                setChange()
            }
        }
    }

    //db의 저장되지 않은 값을 가져옴
    private suspend fun getList() {

        aBuyList = repository.getTradingHistory("A", "01") as ArrayList<String>
        aSellList = repository.getTradingHistory("A", "02") as ArrayList<String>

        bBuyList = repository.getTradingHistory("B", "01") as ArrayList<String>
        bSellList = repository.getTradingHistory("B", "02") as ArrayList<String>

        if (aBuyList.size > aSellList.size) {
            aBuyList.removeAt(aBuyList.size - 1)
        }
        if (bBuyList.size > bSellList.size) {
            bBuyList.removeAt(bBuyList.size - 1)
        }
    }

    // 누적 손익값
    private suspend fun setChange(){
        aChange.value = repository.getChange("A","02")-repository.getChange("A","01")
        bChange.value = repository.getChange("B","02")-repository.getChange("B","01")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private  suspend fun getTradingHistory(){

        val endDay: LocalDate = LocalDate.now()
        val startDay = endDay.minusMonths(1)
        
        repository.getTradingHistory(
            startDay.toString().replace("-",""),
            endDay.toString().replace("-",""),
            "00"
        ).let {
            it!!.body()!!.tradingHistoryList.forEach {
                when {
                    aBuyList.contains(it.odno) -> repository.insert(AutoTrading("A","01",it.odno,it.tot_ccld_amt.toInt()))
                    aSellList.contains(it.odno) -> repository.insert(AutoTrading("A","02",it.odno,it.tot_ccld_amt.toInt()))
                    bBuyList.contains(it.odno) ->repository.insert(AutoTrading("B","01",it.odno,it.tot_ccld_amt.toInt()))
                    bSellList.contains(it.odno) -> repository.insert(AutoTrading("B","02",it.odno,it.tot_ccld_amt.toInt()))
                }
            }
        }
    }
}
