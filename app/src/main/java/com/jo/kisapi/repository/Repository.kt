package com.jo.kisapi.repository

import com.jo.kisapi.*
import com.jo.kisapi.dataModel.*
import com.jo.kisapi.retrofit.IRetrofit
import com.jo.kisapi.retrofit.RetrofitClient

class Repository(private val tokenTimeDao: TokenTimeDao) {

    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(Util.BASE_URL)?.create(IRetrofit::class.java)

    suspend fun insert(token: TokenTime) {
        tokenTimeDao.insert(token)
    }

    suspend fun getToken() =tokenTimeDao.getToken()

    suspend fun getTime() = tokenTimeDao.getTime()

    suspend fun getToken(body: TokenBody) = iRetrofit!!.getToken(body)

    suspend fun getInquireBalance(token: String, inquireBalanceRequest: InquireBalanceRequest) = iRetrofit?.getInquireBalance(
        token,
        inquireBalanceRequest.CANO,
        inquireBalanceRequest.ACNT_PRDT_CD,
        inquireBalanceRequest.AFHR_FLPR_YN,
        inquireBalanceRequest.OFL_YN,
        inquireBalanceRequest.INQR_DVSN,
        inquireBalanceRequest.UNPR_DVSN,
        inquireBalanceRequest.FUND_STTL_ICLD_YN,
        inquireBalanceRequest.FNCG_AMT_AUTO_RDPT_YN,
        inquireBalanceRequest.PRCS_DVSN,
        inquireBalanceRequest.CTX_AREA_FK100,
        inquireBalanceRequest.CTX_AREA_NK100
    )

    suspend fun order(token: String,tr_id: String, orderRequest: OrderRequest) = iRetrofit!!.order(
        token,
        tr_id,
        orderRequest
    )

    suspend fun getTradingHistory(token: String, tradingHistoryRequest: TradingHistoryRequest) = iRetrofit?.getTradingHistory(
        token,
        tradingHistoryRequest.CANO,
        tradingHistoryRequest.ACNT_PRDT_CD,
        tradingHistoryRequest.INQR_STRT_DT,
        tradingHistoryRequest.INQR_END_DT,
        tradingHistoryRequest.SLL_BUY_DVSN_CD,
        tradingHistoryRequest.INQR_DVSN,
        tradingHistoryRequest.PDNO,
        tradingHistoryRequest.CCLD_DVSN,
        tradingHistoryRequest.ORD_GNO_BRNO,
        tradingHistoryRequest.ODNO,
        tradingHistoryRequest.INQR_DVSN_3,
        tradingHistoryRequest.INQR_DVSN_1,
        tradingHistoryRequest.CTX_AREA_FK100,
        tradingHistoryRequest.CTX_AREA_NK100
    )
}