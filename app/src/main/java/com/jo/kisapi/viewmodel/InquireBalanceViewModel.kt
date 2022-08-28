package com.jo.kisapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.dataModel.output1
import com.jo.kisapi.dataModel.output2
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.round

class InquireBalanceViewModel(private val repository: Repository) : ViewModel() {
    val sumPchsAmt = MutableLiveData<Int>(0)
    val sumEvluAmt = MutableLiveData<Int>(0)
    val sumAmt = MutableLiveData<Int>(0)
    val cashes = MutableLiveData<Int>(0)
    val sumEvluPflsAmt = MutableLiveData<Int>(0)
    val rt = MutableLiveData<Float>(0.0f)
    var output1List = MutableLiveData<List<output1>>()
    var output2 = MutableLiveData<output2>()
    val dec = DecimalFormat("#,###원")

    fun getInquireBalance() {

        viewModelScope.launch {
          /*  try {*/
                repository.getInquireBalance(
                    "Bearer " + repository.tokenCheck()
                ).let {

                    output1List.value = it!!.body()!!.output1
                    output2.value = it!!.body()!!.output2[0]

                    sumEvluAmt.value = 0        //평가금액
                    sumPchsAmt.value = 0        //매입금액
                    sumEvluPflsAmt.value = 0    //평가 손익금액
                    (it.body()!!.output1).forEach {
                        sumPchsAmt.value = sumPchsAmt.value?.plus(it.pchs_amt.toInt())
                        sumEvluAmt.value = sumEvluAmt.value?.plus(it.evlu_amt.toInt())
                        sumEvluPflsAmt.value = sumEvluPflsAmt.value?.plus(it.evlu_pfls_amt.toInt())
                        sumAmt.value = sumEvluAmt.value?.plus(cashes.value!!)
                    }
                    rt.value = round((sumEvluPflsAmt.value!!.toFloat() * 1000).div(sumPchsAmt.value!!)).div(10)
                }

            /*} catch (e: Exception) {
            }*/
        }
    }

    fun getCash(){
        viewModelScope.launch {
            repository.getCash(
                "Bearer " + repository.tokenCheck()
            ).let {
                cashes.value = it.body()!!.cashOutput.max_buy_amt
            }
        }
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InquireBalanceViewModel::class.java)) {
                return InquireBalanceViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}