package com.jo.kisapi.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jo.kisapi.AlarmReceiver
import com.jo.kisapi.R
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityMainBinding
import com.jo.kisapi.viewmodel.MyViewModel
import java.util.*


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

        binding.autoTrading!!.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
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

        binding.test3.setOnClickListener {  //주식 체결 내역
            viewModel.getTradingHistory()
        }




        viewModel.msg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }



}