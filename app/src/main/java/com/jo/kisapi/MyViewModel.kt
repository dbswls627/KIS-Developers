package com.jo.kisapi

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class MyViewModel(private val repository: Repository) : ViewModel() {

    private fun getToken(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                repository.getToken(TokenBody(
                    "client_credentials",
                    appkey = Util.API_KEY,
                    Util.API_KEY_SECRET
                )).let {
                    repository.insert(TokenTime("Bearer",it.body()!!.access_token,System.currentTimeMillis().plus(80000000).toString()))
                }
            }catch (e:Exception){

            }
        }
    }

    fun getTokenCheck() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                repository.getTime().let {
                    if (it.toLong() < System.currentTimeMillis()) {
                        getToken()
                    }
                }
            }catch (e:Exception){

            }
        }

    }

    fun getInquireBalance(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                repository.getInquireBalance("Bearer "+repository.getToken(),
                    InquireBalance(
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
                        "")).let {
                    val jsonArray: JsonArray = it!!.body()!!.asJsonObject.get("output1").asJsonArray
                    var arrayList =  ArrayList<output1>()
                    for (i in 0 until jsonArray.size()) {
                        var jsonObject: JsonObject = jsonArray[i].asJsonObject
                        arrayList.add(output1(
                            jsonObject["pdno"].toString().replace("\"",""),
                            jsonObject["prdt_name"].toString().replace("\"",""),
                            jsonObject["hldg_qty"].toString().replace("\"",""),          //매입 개수
                            jsonObject["pchs_avg_pric"].toString().replace("\"",""),     //매입 평균가
                            jsonObject["prpr"].toString().replace("\"",""),              //현재가
                            jsonObject["pchs_amt"].toString().replace("\"",""),          //매입금액
                            jsonObject["evlu_amt"].toString().replace("\"",""),         //평가금액
                            jsonObject["evlu_pfls_amt"].toString().replace("\"",""),  //평가손익금액
                            jsonObject["evlu_pfls_rt"].toString().replace("\"","")     //평가손익률
                        ))
                    } }

            }catch (e:Exception){

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
