package com.jo.kisapi

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {

    @POST("/uapi/hashkey")
    @Headers("content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}"
    )
    fun getHashKey(@Body gd: HashKey): Call<JsonElement>

    @POST("/oauth2/tokenP")
    fun getToken(@Body gd: TokenHeader): Call<Token>

    @GET("/uapi/domestic-stock/v1/trading/inquire-balance")
    @Headers("content-type: application/json",
        "authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjI4NDdlMmI1LTNkZDMtNGExZC1iZDlkLTJhYzJjYmY2MzYzYiIsImlzcyI6InVub2d3IiwiZXhwIjoxNjU5Njk1MjY3LCJpYXQiOjE2NTk2MDg4NjcsImp0aSI6IlBTbDFzSkx2dWtCQnBEUkw4T3c3RDVUYWl0WE5VaDY3VlFHYSJ9.LwTkjRr-zu352p74v_kMz7cKkqivQBW5fsjka_et3blhRI3mEMNtZRmAop7FbNoNLm9jYsaSSZ32Azj5YtWGng",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: TTTC8434R",
        "custtype: P"
    )
    fun getInquireBalance(@Query("CANO") CANO: String,
                          @Query("ACNT_PRDT_CD") ACNT_PRDT_CD: String,
                          @Query("AFHR_FLPR_YN") AFHR_FLPR_YN: String,
                          @Query("OFL_YN") OFL_YN: String,
                          @Query("INQR_DVSN") INQR_DVSN: String,
                          @Query("UNPR_DVSN") UNPR_DVSN: String,
                          @Query("FUND_STTL_ICLD_YN") FUND_STTL_ICLD_YN: String,
                          @Query("FNCG_AMT_AUTO_RDPT_YN") FNCG_AMT_AUTO_RDPT_YN: String,
                          @Query("PRCS_DVSN") PRCS_DVSN: String,
                          @Query("CTX_AREA_FK100") CTX_AREA_FK100: String?,
                          @Query("CTX_AREA_NK100") CTX_AREA_NK100: String?,) :Call<JsonElement>
}
