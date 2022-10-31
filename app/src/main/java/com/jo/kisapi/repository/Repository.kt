package com.jo.kisapi.repository

import com.jo.kisapi.TokenTimeDao
import com.jo.kisapi.Util.API_KEY
import com.jo.kisapi.Util.API_KEY_SECRET
import com.jo.kisapi.Util.CANO
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.dataModel.OrderRequest
import com.jo.kisapi.dataModel.TokenBody
import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.retrofit.IRetrofit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val tokenTimeDao: TokenTimeDao, private val iRetrofit: IRetrofit) {
    /** DB **/
    suspend fun insert(token: TokenTime) = tokenTimeDao.insert(token)


    suspend fun insert(autoTrading: AutoTrading) = tokenTimeDao.insertOrderHistory(autoTrading)


    suspend fun getTime() = tokenTimeDao.getTime()

    suspend fun getTradingHistory(type: String, division: String) =
        tokenTimeDao.getTradingHistory(type, division)

    suspend fun getChange(type: String, division: String) = tokenTimeDao.getChange(type, division)

    /** Rest API **/
    suspend fun getToken() = iRetrofit!!.getToken(
        TokenBody(
            "client_credentials",
            API_KEY,
            API_KEY_SECRET
        )
    )

    suspend fun getInquireBalance() = flow {
        while (true) {
            emit(
                iRetrofit!!.getInquireBalance(
                    tokenTimeDao.getToken(),
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
            )
            delay(500)
        }
    }

    suspend fun order(tr_id: String, PDNO: String, division: String, count: String, amt: String) =
        iRetrofit!!.order(
            tokenTimeDao.getToken(),
            tr_id,
            OrderRequest(
                CANO,
                "01",
                PDNO,   //종목번호
                division,   //지정가 : 00      시장가 : 01     조건부지정가 : 02
                count,     //주문 갯수
                amt,    //주문단가
                ""
            )
    )

    suspend fun getTradingHistory(startDay:String,endDay:String,division:String) =
        iRetrofit?.getTradingHistory(
            tokenTimeDao.getToken(),
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

    suspend fun getCash() = iRetrofit!!.getCash(
        tokenTimeDao.getToken(),
        CANO,
        "01",
        "005930",
        "60000",
        "00",
        "Y",
        "N",
    )

    suspend fun getDailyPrice(FID_INPUT_ISCD: String) = iRetrofit!!.getDailyPrice(
        tokenTimeDao.getToken(),
        "J",
        FID_INPUT_ISCD,
        "D",
        "0"
    )

    suspend fun getCurrentPrice(FID_INPUT_ISCD: String) = flow {
        while (true) {
            emit(
                iRetrofit!!.getCurrentPrice(
                    tokenTimeDao.getToken(),
                    "J",
                    FID_INPUT_ISCD,
                )
            )
            delay(500)
        }

    }

    suspend fun getTimePrice(FID_INPUT_ISCD: String) = iRetrofit!!.getTimePrice(
        tokenTimeDao.getToken(),
        "",
        "J",
        FID_INPUT_ISCD,
        "093000",
        "N"
    )


}