package com.jo.kisapi.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.jo.kisapi.*
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.dataModel.OrderRequest
import com.jo.kisapi.databinding.ActivityMainBinding
import com.jo.kisapi.retrofit.IRetrofit
import com.jo.kisapi.retrofit.RetrofitClient
import com.jo.kisapi.viewmodel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        val iRetrofit: IRetrofit? = RetrofitClient.getClient(Util.BASE_URL)?.create(
            IRetrofit::class.java
        )
        //해쉬
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
        }
        binding.test!!.setOnClickListener {
            viewModel.orderBuy()
        }
        binding.test2!!.setOnClickListener {
            viewModel.orderSell()
        }
        binding.test3.setOnClickListener {
            viewModel.getTradingHistory()
        }


        /*//매수가능금액 조회
        b4!!.setOnClickListener {
            RetrofitManager.instance.getInquireOrder(
                "Bearer "+db!!.TokenTimeDao().getToken(),
                InquireOrder(
                    "73754150",
                    "01",
                    "005930",
                    "70000",
                    "00",
                    "N",
                    "N"
                   )
            ) {
                mTextView!!.text= it
            }
        }*/

    }

}