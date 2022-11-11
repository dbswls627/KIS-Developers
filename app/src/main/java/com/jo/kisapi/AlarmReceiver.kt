package com.jo.kisapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.repository.Repository
import com.jo.kisapi.usecase.InsertTradingHistoryUseCase
import com.jo.kisapi.usecase.PostOrderBuyUseCase
import com.jo.kisapi.usecase.PostOrderSellUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var postOrderBuyUseCase: PostOrderBuyUseCase
    @Inject
    lateinit var postOrderSellUseCase: PostOrderSellUseCase
    @Inject
    lateinit var insertTradingHistoryUseCase: InsertTradingHistoryUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val pdno = intent.getStringExtra("pdno")
        val count = intent.getStringExtra("count")
        val amt = intent.getStringExtra("amt")

        orderBuy(pdno!!, count!!,amt!!)


    }

    private fun orderBuy(pdno: String, count: String, amt: String) {

        CoroutineScope(Dispatchers.IO).launch {

            postOrderBuyUseCase(
                pdno!!,
                "01",
                count!!,
              "0"
            ).let {
                try {
                    insertTradingHistoryUseCase(AutoTrading("B", "01", it.output.ODNO, 0))
                    orderSell(pdno, count, amt)
                }catch (e:Exception){}
            }
        }
    }

    private fun orderSell(pdno: String, count: String, amt: String) {
        CoroutineScope(Dispatchers.IO).launch {
            postOrderSellUseCase(
                pdno,
                "02",
                count,
                amt
            ).let {
                insertTradingHistoryUseCase(AutoTrading("B", "02", it.output.ODNO, 0))

            }
        }

    }

}