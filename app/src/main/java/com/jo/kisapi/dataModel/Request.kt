package com.jo.kisapi.dataModel

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

