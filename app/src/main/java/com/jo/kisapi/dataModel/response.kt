package com.jo.kisapi.dataModel

import com.google.gson.annotations.SerializedName

data class Token(
    val access_token: String
)

data class InquireBalance(
    val output1: List<output1>,
    val output2: List<output2>
)

data class output1(
    val pdno: String,
    val prdt_name: String,
    val hldg_qty: Int,           //보유수량
    val pchs_avg_pric: Float,      //매입평균금액
    val prpr: Int,               //현재가
    val pchs_amt: Int,           //매입금액
    val evlu_amt: Int,           //평가금액
    val evlu_pfls_amt: Int,      //평가손익금액
    val evlu_pfls_rt: Float        //평가손익률
)

data class output2(
    val dnca_tot_amt: Int,   //예수금 총액
    val tot_evlu_amt: Int,   //총평가 금액
    val nass_amt: Int        //순자산 금액
)

data class OrderResponse(
    @SerializedName("msg1")
    val msg1: String,
    @SerializedName("rt_cd")
    val rt_cd: String,
    val output: output
)

data class output(
    val odno: String
)

data class Hash(
    val HASH: String
)

data class Cash(
    @SerializedName("output")
    val cashOutput: CashOutput
)


data class CashOutput(
    @SerializedName("max_buy_amt")
    val max_buy_amt: Int
)

data class DailyPrice(
    @SerializedName("output")
    val DailyPriceList: List<Dailyprpr>
)

data class Dailyprpr(
    val stck_oprc: String,
    val stck_hgpr: String,
    val stck_lwpr: String,
    val stck_clpr: String,
)

data class CurrentPrice(
    @SerializedName("output")
    val prpr:prpr

)

data class prpr(
    val stck_prpr: Int,
    val prdy_vrss: Int,
    val prdy_ctrt: Double
)