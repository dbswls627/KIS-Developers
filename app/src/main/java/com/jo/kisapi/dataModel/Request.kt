package com.jo.kisapi.dataModel

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

data class TradingHistoryRequest(
    val CANO: String,
    val ACNT_PRDT_CD: String,
    val INQR_STRT_DT: String,
    val INQR_END_DT: String,
    val SLL_BUY_DVSN_CD: String,
    val INQR_DVSN: String,
    val PDNO: String,
    val CCLD_DVSN: String,
    val ORD_GNO_BRNO: String,
    val ODNO: String,
    val INQR_DVSN_3: String,
    val INQR_DVSN_1: String,
    val CTX_AREA_FK100: String,
    val CTX_AREA_NK100: String
)

data class CashRequest(
    val CANO: String,
    val ACNT_PRDT_CD: String,
    val PDNO: String,
    val ORD_UNPR: String,
    val ORD_DVSN: String,
    val CMA_EVLU_AMT_ICLD_YN: String,
    val OVRS_ICLD_YN: String
)
