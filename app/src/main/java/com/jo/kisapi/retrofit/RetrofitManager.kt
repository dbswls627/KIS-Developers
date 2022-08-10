package com.jo.kisapi.retrofit

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.jo.kisapi.*
import com.jo.kisapi.Util.BASE_URL
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response


class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(BASE_URL)?.create(IRetrofit::class.java)

    fun getHashKey(hashKey: HashKey?, completion: (String) -> Unit) {

       // val term: HashKey = (hashKey.let { it } ?: null) as HashKey   //let 비어잇으면 "" 아니면 it

        val call = iRetrofit?.getHashKey(hashKey!!).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            //응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("로그","성공")
              //  completion(response.raw().toString())
            }
            //응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("로그","실패")

            }

        })

    }

    fun getToken(token: TokenBody?, completion: (String) -> Unit) {

        val call = iRetrofit?.getToken(token!!).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<Token>{
            //응답 성공
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                Log.d("로그",response.body()!!.toString())
                  completion(response.body()!!.access_token)
            }
            //응답 실패
            override fun onFailure(call: Call<Token>, t: Throwable) {

            }

        })

    }

    fun getInquireBalance(token:String,inquireBalance: InquireBalance, completion: (ArrayList<output1>) -> Unit) {

        val call = iRetrofit?.getInquireBalance(
            token,
            inquireBalance.CANO,
            inquireBalance.ACNT_PRDT_CD,
            inquireBalance.AFHR_FLPR_YN,
            inquireBalance.OFL_YN,
            inquireBalance.INQR_DVSN,
            inquireBalance.UNPR_DVSN,
            inquireBalance.FUND_STTL_ICLD_YN,
            inquireBalance.FNCG_AMT_AUTO_RDPT_YN,
            inquireBalance.PRCS_DVSN,
            inquireBalance.CTX_AREA_FK100,
            inquireBalance.CTX_AREA_NK100
        ).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            //응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("로그","성공")

                val jsonArray: JsonArray = response.body()!!.asJsonObject.get("output1").asJsonArray
                var arrayList =  ArrayList<output1>()
                for (i in 0 until jsonArray.size()) {
                    var jsonObject: JsonObject = jsonArray[i].asJsonObject
                        arrayList.add(output1(
                        jsonObject["pdno"].toString(),
                        jsonObject["prdt_name"].toString(),
                        jsonObject["hldg_qty"].toString(),          //매입 개수
                        jsonObject["pchs_avg_pric"].toString(),     //매입 평균가
                        jsonObject["prpr"].toString(),              //현재가
                        jsonObject["evlu_amt"].toString(),         //평가금액
                        jsonObject["evlu_pfls_amt"].toString(), //평가손익금액
                        jsonObject["evlu_pfls_rt"].toString(),     //평가손익률
                        jsonObject["evlu_erng_rt"].toString() //평가수익률
                    ))
                }
                if (arrayList != null) {
                    completion(arrayList)
                }
            }
            //응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun getInquireOrder(token:String,inquireOrder: InquireOrder, completion: (String) -> Unit) {

        val call = iRetrofit?.getInquireOrder(
            token,
            inquireOrder.CANO,
            inquireOrder.ACNT_PRDT_CD,
            inquireOrder.PDNO,
            inquireOrder.ORD_UNPR,
            inquireOrder.ORD_DVSN,
            inquireOrder.CMA_EVLU_AMT_ICLD_YN,
            inquireOrder.OVRS_ICLD_YN

        ).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            //응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("로그","성공")
                Log.d("로그",response.toString())

                completion(response.body().toString())
            }
            //응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}