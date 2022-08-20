package com.jo.kisapi.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jo.kisapi.*
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.dataModel.HashKeyBody
import com.jo.kisapi.dataModel.OrderRequest
import com.jo.kisapi.retrofit.IRetrofit
import com.jo.kisapi.retrofit.RetrofitClient
import com.jo.kisapi.viewmodel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var b: Button? = null
    var test: Button? = null
    var b3: Button? = null
    var b4: Button? = null

    private val viewModel: MyViewModel by viewModels {
        MyViewModel.Factory((application as KISApplication).repository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b = findViewById(R.id.button)
        b3 = findViewById(R.id.button3)
        b4 = findViewById(R.id.button4)
        test = findViewById(R.id.test)


        //토큰 갱신
        viewModel.getTokenCheck()

        //주식잔고 조회
        b3!!.setOnClickListener {
            val intent = Intent(this,InquireBalanceActivity::class.java)
            startActivity(intent)
        }
         val iRetrofit: IRetrofit? = RetrofitClient.getClient(Util.BASE_URL)?.create(
            IRetrofit::class.java)
        //해쉬
        b!!.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val a = iRetrofit?.getHashKey(OrderRequest(
                    "73754150",
                    "01",
                    "011690",
                    "01",
                    "1",
                    "0",
                    ""))?.body()?.HASH.toString()
                Log.d("테스트",a)
            }
        }
        test!!.setOnClickListener {
            viewModel.order()
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