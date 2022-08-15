package com.jo.kisapi

import androidx.room.Entity
import androidx.room.PrimaryKey

data class HashKey(
    val CANO: String,
    val ACNT_PRDT_CD: String)

data class TokenBody(
    val grant_type: String,
    val appkey: String,
    val appsecret: String)

data class Token(
    val access_token: String)

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

data class InquireOrder(
    val CANO: String,
    val ACNT_PRDT_CD: String,
    val PDNO: String,
    val ORD_UNPR: String,
    val ORD_DVSN: String,
    val CMA_EVLU_AMT_ICLD_YN: String,
    val OVRS_ICLD_YN: String,
)
data class InquireBalance(
    val output1: List<output1>,
    val output2: List<output2>
)
data class output1(
    val pdno: String,
    val prdt_name: String,
    val hldg_qty: String,           //보유수량
    val pchs_avg_pric: String,      //매입평균금액
    val prpr: String,               //현재가
    val pchs_amt: String,           //매입금액
    val evlu_amt: String,           //평가금액
    val evlu_pfls_amt: String,      //평가손익금액
    val evlu_pfls_rt: String        //평가손익률
)

data class output2(
    val dnca_tot_amt: String,   //예수금 총액
    val tot_evlu_amt: String,   //총평가 금액
    val nass_amt: String        //순자산 금액
)

@Entity
data class TokenTime(
    @PrimaryKey
    var type:String,
    var token:String,
    var time:String
)


