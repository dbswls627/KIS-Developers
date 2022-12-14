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

    suspend fun getBuySum(type: String) = tokenTimeDao.getSum(type,"01")

    suspend fun getSellSum(type: String) = tokenTimeDao.getSum(type,"02")

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

    suspend fun orderBuy(PDNO: String, division: String, count: String, amt: String) =
        iRetrofit!!.order(
            tokenTimeDao.getToken(),
            Util.buy,
            OrderRequest(
                CANO,
                "01",
                PDNO,   //????????????
                division,   //????????? : 00      ????????? : 01     ?????????????????? : 02
                count,     //?????? ??????
                amt,    //????????????
                ""
            )
        )

    suspend fun orderSell(PDNO: String, division: String, count: String, amt: String) =
        iRetrofit!!.order(
            tokenTimeDao.getToken(),
            Util.sell,
            OrderRequest(
                CANO,
                "01",
                PDNO,   //????????????
                division,   //????????? : 00      ????????? : 01     ?????????????????? : 02
                count,     //?????? ??????
                amt,    //????????????
                ""
            )
        )

    suspend fun getTradingHistory(startDay: String, endDay: String, division: String) =
        iRetrofit.getTradingHistory(
            tokenTimeDao.getToken(),
            CANO,
            "01",
            startDay,
            endDay,
            division,           //??????:00 ??????:01 ??????:02
            "00",    //??????
            "",
            "01",   //??????:00 ??????:01 ?????????:02
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