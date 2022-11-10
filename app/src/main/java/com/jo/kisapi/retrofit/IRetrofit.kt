package com.jo.kisapi.retrofit

import com.google.gson.JsonElement
import com.jo.kisapi.Util
import com.jo.kisapi.dataModel.*
import retrofit2.Response
import retrofit2.http.*

interface IRetrofit {

    //해쉬키
    @POST("/uapi/hashkey")
    @Headers("content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}"
    )
    suspend fun getHashKey(@Body orderRequest: OrderRequest): Response<Hash>


    //토큰 갱신
    @POST("/oauth2/tokenP")
    suspend  fun getToken(@Body body: TokenBody): Token


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
        @Query("CTX_AREA_NK100") CTX_AREA_NK100: String?
    ): InquireBalance



    //주식 주문
    @POST("/uapi/domestic-stock/v1/trading/order-cash")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "custtype: P"
    )
    suspend fun order(
        @Header("Authorization") token: String,
        @Header("tr_id") tr_id: String,
        @Body orderRequest: OrderRequest
    ): Response<OrderResponse>

    //주식 체결 조회
    @GET("/uapi/domestic-stock/v1/trading/inquire-daily-ccld")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: TTTC8001R",
        "custtype: P"
    )
    suspend fun getTradingHistory(
        @Header("Authorization") token: String,
        @Query("CANO") CANO: String,
        @Query("ACNT_PRDT_CD") ACNT_PRDT_CD: String,
        @Query("INQR_STRT_DT") INQR_STRT_DT: String,
        @Query("INQR_END_DT") INQR_END_DT: String,
        @Query("SLL_BUY_DVSN_CD") SLL_BUY_DVSN_CD: String,
        @Query("INQR_DVSN") INQR_DVSN: String,
        @Query("PDNO") PDNO: String,
        @Query("CCLD_DVSN") CCLD_DVSN: String,
        @Query("ORD_GNO_BRNO") IORD_GNO_BRNO: String,
        @Query("ODNO") ODNO: String,
        @Query("INQR_DVSN_3") INQR_DVSN_3: String,
        @Query("INQR_DVSN_1") INQR_DVSN_1: String,
        @Query("CTX_AREA_FK100") CTX_AREA_FK100: String,
        @Query("CTX_AREA_NK100") CTX_AREA_NK100: String,
    ): TradingHistoryList

    //매수가능금액 조회
    @GET("/uapi/domestic-stock/v1/trading/inquire-psbl-order")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: TTTC8908R",
        "custtype: P"
    )
    suspend fun getCash(
        @Header("Authorization") token: String,
        @Query("CANO") CANO: String,
        @Query("ACNT_PRDT_CD") ACNT_PRDT_CD: String,
        @Query("PDNO") PDNO: String,
        @Query("ORD_UNPR") ORD_UNPR: String,
        @Query("ORD_DVSN") ORD_DVSN: String,
        @Query("CMA_EVLU_AMT_ICLD_YN") CMA_EVLU_AMT_ICLD_YN: String,
        @Query("OVRS_ICLD_YN") OVRS_ICLD_YN: String
    ): Response<Cash>


    //일자별 현재가 조회
    @GET(" /uapi/domestic-stock/v1/quotations/inquire-daily-price")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: FHKST01010400",
        "custtype: P"
    )
    suspend fun getDailyPrice(
        @Header("Authorization") token: String,
        @Query("FID_COND_MRKT_DIV_CODE") FID_COND_MRKT_DIV_CODE: String,
        @Query("FID_INPUT_ISCD") FID_INPUT_ISCD: String,
        @Query("FID_PERIOD_DIV_CODE") FID_PERIOD_DIV_CODE: String,
        @Query("FID_ORG_ADJ_PRC") FID_ORG_ADJ_PRC: String,
    ): Response<DailyPrice>


    // 현재가 조회
    @GET("/uapi/domestic-stock/v1/quotations/inquire-price")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: FHKST01010100",
        "custtype: P"
    )
    suspend fun getCurrentPrice(
        @Header("Authorization") token: String,
        @Query("FID_COND_MRKT_DIV_CODE") FID_COND_MRKT_DIV_CODE: String,
        @Query("FID_INPUT_ISCD") FID_INPUT_ISCD: String,
    ): CurrentPrice

    //분봉 조회
    @GET("/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice")
    @Headers(
        "content-type: application/json",
        "appkey: ${Util.API_KEY}",
        "appsecret: ${Util.API_KEY_SECRET}",
        "tr_id: FHKST03010200",
        "custtype: P"
    )
    suspend fun getTimePrice(
        @Header("Authorization") token: String,
        @Query("FID_ETC_CLS_CODE") FID_ETC_CLS_CODE: String,
        @Query("FID_COND_MRKT_DIV_CODE") FID_COND_MRKT_DIV_CODE: String,
        @Query("FID_INPUT_ISCD") FID_INPUT_ISCD: String,
        @Query("FID_INPUT_HOUR_1") FID_INPUT_HOUR_1: String,
        @Query("FID_PW_DATA_INCU_YN") FID_PW_DATA_INCU_YN: String
    ): Response<TimePrice>

}
