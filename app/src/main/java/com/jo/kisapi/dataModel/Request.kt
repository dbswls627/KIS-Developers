package com.jo.kisapi.dataModel

import retrofit2.http.Header
import retrofit2.http.Query

data class InquireBalanceRequest(
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

data class HashKeyBody(
    val CANO: String,
    val ACNT_PRDT_CD: String)


data class TokenBody(
    val grant_type: String,
    val appkey: String,
    val appsecret: String)

data class OrderRequest(
    val CANO: String,
    val ACNT_PRDT_CD: String,
    val PDNO: String,
    val ORD_DVSN: String,
    val ORD_QTY: String,
    val ORD_UNPR: String,
    val ALGO_NO: String?
)
