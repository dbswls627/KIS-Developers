package com.jo.kisapi.viewmodel

import android.util.Log
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
    val rt = MutableLiveData<Double>(0.0)
    var output1List = MutableLiveData<List<output1>>()
    var list = ArrayList<output1>()
    val dec = DecimalFormat("#,###원")

    fun getInquireBalance() {

        viewModelScope.launch {
          /*  try {*/
                repository.getInquireBalance(
                    "Bearer " + repository.dbToken()
                ).let {
                    list.clear()

                    sumPchsAmt.value =it!!.body()!!.output2[0].pchs_amt_smtl_amt
                    sumEvluAmt.value= it!!.body()!!.output2[0].evlu_amt_smtl_amt
                    sumEvluPflsAmt.value = it!!.body()!!.output2[0].evlu_pfls_smtl_amt
                    rt.value = round((sumEvluPflsAmt.value!!.toDouble() * 1000).div(sumPchsAmt.value!!)).div(10)
                    sumAmt.value = sumEvluAmt.value?.plus(cashes.value!!)
                    (it!!.body()!!.output1).forEach {
                        if(it.hldg_qty !=0){
                            list.add(it)
                        }
                    }
                    output1List.value = list

                }

            /*} catch (e: Exception) {
            }*/
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

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InquireBalanceViewModel::class.java)) {
                return InquireBalanceViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}