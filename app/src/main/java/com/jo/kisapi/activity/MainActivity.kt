package com.jo.kisapi.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jo.kisapi.*
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.viewmodel.MyViewModel


class MainActivity : AppCompatActivity() {

    private var b: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var mTextView: TextView? = null

    private val viewModel: MyViewModel by viewModels {
        MyViewModel.Factory((application as KISApplication).repository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b = findViewById(R.id.button)
        b2 = findViewById(R.id.button2)
        b3 = findViewById(R.id.button3)
        b4 = findViewById(R.id.button4)
        mTextView = findViewById(R.id.text)


        //토큰 갱신
        viewModel.getTokenCheck()

        //주식잔고 조회
        b3!!.setOnClickListener {
            val intent = Intent(this,InquireBalanceActivity::class.java)
            startActivity(intent)
        }

        //해쉬
        /*b!!.setOnClickListener {
            RetrofitManager.instance.getHashKey(HashKey("73754150", "01")) {
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }*/



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