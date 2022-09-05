package com.jo.kisapi.repository

import com.jo.kisapi.TokenTimeDao
import com.jo.kisapi.Util
import com.jo.kisapi.Util.API_KEY
import com.jo.kisapi.Util.API_KEY_SECRET
import com.jo.kisapi.Util.CANO
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.dataModel.OrderRequest
import com.jo.kisapi.dataModel.TokenBody
import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.retrofit.IRetrofit
import com.jo.kisapi.retrofit.RetrofitClient

class Repository(private val tokenTimeDao: TokenTimeDao) {

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(Util.BASE_URL)?.create(IRetrofit::class.java)

    suspend fun insert(token: TokenTime) {
        tokenTimeDao.insert(token)
    }

    suspend fun insert(autoTrading: AutoTrading) {
        tokenTimeDao.insertOrderHistory(autoTrading)
    }

    suspend fun dbToken() = tokenTimeDao.getToken()

    suspend fun getTime() = tokenTimeDao.getTime()

    suspend fun getToken() = iRetrofit!!.getToken(
        TokenBody(
            "client_credentials",
            API_KEY,
            API_KEY_SECRET
        )
    )

    suspend fun getInquireBalance(token: String) =
        iRetrofit?.getInquireBalance(
            token,
            CANO,
            "01",
            "N",
            "",
            "01",
            "01",
            "N",
            "N",
            "00",
            "",
            ""
        )

    suspend fun order(token: String, tr_id: String,PDNO: String,count :String) = iRetrofit!!.order(
        token,
        tr_id,
        OrderRequest(
            CANO,
            "01",
            PDNO,   //종목번호
            "01",   //지정가 : 00 시장가 : 01
            count,     //주문 갯수
            "0",    //주문단가
            ""
        )
    )

    suspend fun getTradingHistory(token: String,startDay:String,endDay:String,division:String) =
        iRetrofit?.getTradingHistory(
            token,
            CANO,
            "01",
            startDay,
            endDay,
            division,           //전체:00 매도:01 매수:02
            "00",    //정렬
            "",
            "01",   //전체:00 체결:01 미체결:02
            "",
            "",
            "",
            "",
            "",
            ""
        )

    suspend fun getCash(token: String) = iRetrofit!!.getCash(
        token,
        CANO,
        "01",
        "005930",
        "60000",
        "00",
        "Y",
        "N",
    )

    suspend fun getDailyPrice(token: String, FID_INPUT_ISCD: String) = iRetrofit!!.getDailyPrice(
        token,
        "J",
        FID_INPUT_ISCD,
        "D",
        "0"
        )

    suspend fun getCurrentPrice(token: String, FID_INPUT_ISCD: String) = iRetrofit!!.getCurrentPrice(
        token,
        "J",
        FID_INPUT_ISCD,
    )
}