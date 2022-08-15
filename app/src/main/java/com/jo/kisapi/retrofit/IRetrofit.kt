package com.jo.kisapi.retrofit

import com.google.gson.JsonElement
import com.jo.kisapi.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IRetrofit {

    //해쉬키
    @POST("/uapi/hashkey")
    @Headers("content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}"
    )
    fun getHashKey(@Body gd: HashKey): Call<JsonElement>

    //토큰
    @POST("/oauth2/tokenP")
    suspend  fun getToken(@Body body: TokenBody): Response<Token>

    //주식잔고 조회
    @GET("/uapi/domestic-stock/v1/trading/inquire-balance")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: TTTC8434R",
        "custtype: P"
    )
    suspend fun getInquireBalance(
        @Header("Authorization") token: String,
        @Query("CANO") CANO: String,
        @Query("ACNT_PRDT_CD") ACNT_PRDT_CD: String,
        @Query("AFHR_FLPR_YN") AFHR_FLPR_YN: String,
        @Query("OFL_YN") OFL_YN: String,
        @Query("INQR_DVSN") INQR_DVSN: String,
        @Query("UNPR_DVSN") UNPR_DVSN: String,
        @Query("FUND_STTL_ICLD_YN") FUND_STTL_ICLD_YN: String,
        @Query("FNCG_AMT_AUTO_RDPT_YN") FNCG_AMT_AUTO_RDPT_YN: String,
        @Query("PRCS_DVSN") PRCS_DVSN: String,
        @Query("CTX_AREA_FK100") CTX_AREA_FK100: String?,
        @Query("CTX_AREA_NK100") CTX_AREA_NK100: String?,
    ): Response<InquireBalance>

    //매수가능금액 조회
    @GET("/uapi/domestic-stock/v1/trading/inquire-psbl-order")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: TTTC8908R",
        "custtype: P"
    )
    fun getInquireOrder(
        @Header("Authorization") token: String,
        @Query("CANO") CANO: String,
        @Query("ACNT_PRDT_CD") ACNT_PRDT_CD: String,
        @Query("PDNO") PDNO: String,
        @Query("ORD_UNPR") ORD_UNPR: String,
        @Query("ORD_DVSN") ORD_DVSN: String,
        @Query("CMA_EVLU_AMT_ICLD_YN") CMA_EVLU_AMT_ICLD_YN: String,
        @Query("OVRS_ICLD_YN") OVRS_ICLD_YN: String
    ): Call<JsonElement>
}
