package com.jo.kisapi

import retrofit2.http.Query

data class HashKey(
    val CANO: String,
    val ACNT_PRDT_CD: String)

data class TokenHeader(
    val grant_type: String,
    val appkey: String,
    val appsecret: String)

data class Token(
    val access_token: String,
    val token_type: String,
    val expires_in: String)

data class InquireBalance(
    val CANO: String,
    val ACNT_PRDT_CD: String,
    val AFHR_FLPR_YN: String,
    val OFL_YN:String,
    val INQR_DVSN: String,
    val UNPR_DVSN: String,
    val FUND_STTL_ICLD_YN: String,
    val FNCG_AMT_AUTO_RDPT_YN: String,
    val PRCS_DVSN: String,
    val CTX_AREA_FK100: String?,
    val CTX_AREA_NK100: String?
)

data class InquireOrder(
    val CANO: String,
    val ACNT_PRDT_CD: String,
    val PDNO: String,
    val ORD_UNPR: String,
    val ORD_DVSN: String,
    val CMA_EVLU_AMT_ICLD_YN: String,
    val OVRS_ICLD_YN: String,
)

