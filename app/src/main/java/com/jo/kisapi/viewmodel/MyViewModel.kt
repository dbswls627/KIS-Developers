package com.jo.kisapi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jo.kisapi.Util
import com.jo.kisapi.Util.buy
import com.jo.kisapi.Util.sell
import com.jo.kisapi.dataModel.OrderRequest
import com.jo.kisapi.dataModel.TokenBody
import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.dataModel.TradingHistoryRequest
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.launch


class MyViewModel(private val repository: Repository) : ViewModel() {

    private fun getToken(){

        viewModelScope.launch{
            try {
                repository.getToken(
                    TokenBody(
                    "client_credentials",
                    appkey = Util.API_KEY,
                    Util.API_KEY_SECRET
                )
                ).let {
                    repository.insert(TokenTime("Bearer",it.body()!!.access_token,System.currentTimeMillis().plus(80000000).toString()))
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

    fun orderBuy() {
        viewModelScope.launch {
            repository.order(
                "Bearer " + repository.getToken(),
                buy,
                OrderRequest(
                    "73754150",
                    "01",
                    "011690",
                    "01",
                    "1",
                    "0",
                    ""
                )
            ).let {
                Log.d("test", it.body()!!.toString())
            }
        }

    }

    fun orderSell() {
        viewModelScope.launch {
            repository.order(
                "Bearer " + repository.getToken(),
                sell,
                OrderRequest(
                    "73754150",
                    "01",
                    "011690",
                    "01",
                    "1",
                    "0",
                    ""
                )
            ).let {
                Log.d("test", it.body()!!.toString())
            }
        }

    }

    fun getTradingHistory() {
        viewModelScope.launch {
            repository.getTradingHistory(
                "Bearer " + repository.getToken(),
                TradingHistoryRequest(
                    "73754150",
                    "01",
                    "20220801",
                    "20220822",
                    "00",
                    "00",
                    "",
                    "00",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
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
