package com.jo.kisapi.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.jo.kisapi.*
import com.jo.kisapi.Util.BASE_URL
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

    fun getToken(token: TokenHeader?, completion: (String) -> Unit) {

      //  val term: TokenHeader = (token.let { it } ?: null) as TokenHeader   //let 비어잇으면 "" 아니면 it
        val call = iRetrofit?.getToken(token!!).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<Token>{
            //응답 성공
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                Log.d("로그",response.body()!!.toString())
                  completion(response.body()!!.access_token)
            }
            //응답 실패
            override fun onFailure(call: Call<Token>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun getInquireBalance(inquireBalance: InquireBalance, completion: (String) -> Unit) {

        val call = iRetrofit?.getInquireBalance(
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
                Log.d("로그",response.toString())
                Log.d("로그",response.body().toString())
                //  completion(response.raw().toString())
            }
            //응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}