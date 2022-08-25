package com.jo.kisapi.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jo.kisapi.*
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityMainBinding
import com.jo.kisapi.viewmodel.MyViewModel


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels {
        MyViewModel.Factory((application as KISApplication).repository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        //토큰 갱신
        viewModel.getTokenCheck()

        //주식잔고 조회

        binding.button3!!.setOnClickListener {
            val intent = Intent(this, InquireBalanceActivity::class.java)
            startActivity(intent)
        }

/*        //해쉬
        binding.button!!.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val a = iRetrofit?.getHashKey(
                    OrderRequest(
                        "73754150",
                        "01",
                        "011690",
                        "01",
                        "1",
                        "0",
                        ""
                    )
                )?.body()?.HASH.toString()
                Log.d("테스트", a)
            }
        }*/
        binding.test!!.setOnClickListener { //주식 매수
            viewModel.orderBuy()
        }
        binding.test2!!.setOnClickListener { //주식 매도
            viewModel.orderSell()
        }
        binding.test3.setOnClickListener {  //주식 체결 내역
            viewModel.getTradingHistory()
        }
        binding.test4.setOnClickListener {

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)  // 1
            val pendingIntent = PendingIntent.getBroadcast(     // 2
                this, AlarmReceiver.NOTIFICATION_ID, intent,
                 PendingIntent.FLAG_IMMUTABLE
            )

                val triggerTime = (SystemClock.elapsedRealtime()  // 4
                        + 5 * 1000)
                alarmManager.set(   // 5
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
                "Onetime Alarm On"
            Log.d("test", "toastMessage")

        }
    }

}