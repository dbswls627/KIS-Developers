package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.InquireBalanceRequest
import com.jo.kisapi.output1
import com.jo.kisapi.output2
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.launch

class InquireBalanceViewModel(private val repository: Repository) : ViewModel() {

    var output1List = MutableLiveData<List<output1>>()
    var output2 = MutableLiveData<output2>()

    fun getInquireBalance() {

        viewModelScope.launch {
          //  try {
                repository.getInquireBalance(
                    "Bearer " + repository.getToken(),
                    InquireBalanceRequest(
                        "73754150",
                        "01",
                        "N",
                        "",
                        "01",
                        "01",
                        "N",
                        "N",
                        "01",
                        "",
                        ""
                    )
                ).let {

                    output2.value =  it!!.body()!!.output2[0]
                    output1List.value = it!!.body()!!.output1
                }

           /* } catch (e: Exception) {
                Log.d("로그", e.toString())
            }*/
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