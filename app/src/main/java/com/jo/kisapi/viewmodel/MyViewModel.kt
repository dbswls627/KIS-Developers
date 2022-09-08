package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.Util.buy
import com.jo.kisapi.Util.sell
import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.launch


class MyViewModel(private val repository: Repository) : ViewModel() {

    val msg = MutableLiveData<String>()

    private fun getToken(){
        viewModelScope.launch{
            try {
                repository.getToken()
                    .let {
                    repository.insert(
                        TokenTime(
                            "Bearer",
                            it.body()!!.access_token,
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


    fun getTradingHistory() {
        viewModelScope.launch {
            repository.getTradingHistory(
                "Bearer " + repository.dbToken(),
                "20220908" ,
                "20220908",
                "00"
            ).let {
                Log.d("test", it!!.body().toString())
            }
        }
    }




    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
                return MyViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
