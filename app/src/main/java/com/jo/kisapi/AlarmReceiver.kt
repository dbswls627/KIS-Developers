package com.jo.kisapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    override fun onReceive(context: Context, intent: Intent) {
        val pdno = intent.getStringExtra("pdno")
        val count = intent.getStringExtra("count")
        val amt = intent.getStringExtra("amt")

        orderBuy(pdno!!, count!!,amt!!)


    }

    private fun orderBuy(pdno: String, count: String, amt: String) {

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Test",count)
            repository.order(
                Util.buy,
                pdno!!,
                "00",
                count!!,
                getCurrentPrice(pdno)
            ).let {
                repository.insert(AutoTrading("B", "01", it.body()!!.output.ODNO, 0))
                    orderSell(pdno, count, amt)
            }
        }
    }

    private fun orderSell(pdno: String, count: String, amt: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.order(
                Util.sell,
                pdno,
                "02",
                count,
                amt
            ).let {
                Log.d("test", it.body().toString())
                repository.insert(AutoTrading("B", "02", it.body()!!.output.ODNO, 0))

            }
        }

    }

    private suspend fun getCurrentPrice(no: String): String = repository.getCurrentPrice(no).body()!!.prpr.stck_prpr.toString()
}