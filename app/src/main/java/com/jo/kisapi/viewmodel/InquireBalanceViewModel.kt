package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.dataModel.output1
import com.jo.kisapi.dataModel.output2
import com.jo.kisapi.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.round
@HiltViewModel
class InquireBalanceViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val sumPchsAmt = MutableStateFlow<Int>(0)
    val sumEvluAmt = MutableStateFlow<Int>(0)
    val sumAmt = MutableStateFlow<Int>(0)
    val cashes = MutableLiveData<Int>(0)
    val sumEvluPflsAmt = MutableStateFlow<Int>(0)
    val rt = MutableLiveData<Double>(0.0)
    var output1List = MutableLiveData<List<output1>>()
    var list = ArrayList<output1>()
    val dec = DecimalFormat("#,###원")

    fun getInquireBalance() {

        viewModelScope.launch {
          /*  try {*/
                repository.getInquireBalance(
                ).collect {
                    list.clear()

                    sumPchsAmt.value =it.output2[0].pchs_amt_smtl_amt
                    sumEvluAmt.value= it.output2[0].evlu_amt_smtl_amt
                    sumEvluPflsAmt.value = it.output2[0].evlu_pfls_smtl_amt
                    rt.value = round((sumEvluPflsAmt.value.toDouble() * 1000).div(sumPchsAmt.value)).div(10)
                    sumAmt.value = sumEvluAmt.value.plus(cashes.value!!)
                    (it.output1).forEach {
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
            repository.getCash().let {
                cashes.value = it.body()!!.cashOutput.max_buy_amt
            }
        }
    }


}