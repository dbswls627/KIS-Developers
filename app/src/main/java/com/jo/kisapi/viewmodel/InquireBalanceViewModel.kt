package com.jo.kisapi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.dataModel.output1
import com.jo.kisapi.usecase.GetBalanceUseCase
import com.jo.kisapi.usecase.GetMyCashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class InquireBalanceViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getMyCashUseCase: GetMyCashUseCase
) : ViewModel() {
    val dec = DecimalFormat("#,###원")
    val sumPchsAmt = MutableStateFlow<Int>(0)
    val sumEvluAmt = MutableStateFlow<Int>(0)
    val sumAmt = MutableStateFlow<Int>(0)
    val cashes = mutableStateOf<Int>(0)
    val sumEvluPflsAmt = MutableStateFlow<Int>(0)
    val rt = MutableStateFlow<Double>(0.0)
    var output1List = mutableStateListOf<output1>()

    fun getInquireBalance() {

        viewModelScope.launch {
          /*  try {*/
                getBalanceUseCase(
                ).collect {

                    sumPchsAmt.value =it.output2[0].pchs_amt_smtl_amt
                    sumEvluAmt.value= it.output2[0].evlu_amt_smtl_amt
                    sumEvluPflsAmt.value = it.output2[0].evlu_pfls_smtl_amt
                    rt.value = round((sumEvluPflsAmt.value.toDouble() * 1000).div(sumPchsAmt.value)).div(10)
                    sumAmt.value = sumEvluAmt.value.plus(cashes.value!!)

                    (it.output1).forEach {
                        if(it.hldg_qty !=0){
                            output1List.add(it)
                        }
                    }

                }

            /*} catch (e: Exception) {
            }*/
        }
    }

    fun getCash(){
        viewModelScope.launch {
                getMyCashUseCase().let{
                cashes.value = it.cashOutput.max_buy_amt
            }
        }
    }


}