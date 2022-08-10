package com.jo.kisapi.activity

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jo.kisapi.*
import com.jo.kisapi.retrofit.RetrofitManager


class MainActivity : AppCompatActivity() {

    private var b: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var mTextView: TextView? = null
    var db : AppDatabase? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)

        b = findViewById(R.id.button)
        b2 = findViewById(R.id.button2)
        b3 = findViewById(R.id.button3)
        b4 = findViewById(R.id.button4)
        mTextView = findViewById(R.id.text)

        //토큰 갱신
         if (db!!.TokenTimeDao().getTime()!!.toLong() < System.currentTimeMillis()){
             RetrofitManager.instance.getToken(
                 TokenBody(
                     "client_credentials",
                     appkey = Util.API_KEY,
                     Util.API_KEY_SECRET
                 )
             ) {
                 db?.TokenTimeDao()!!.insert(TokenTime("Bearer",it,System.currentTimeMillis().plus(80000000).toString()))
             }
         }
        //해쉬
        b!!.setOnClickListener {
            RetrofitManager.instance.getHashKey(HashKey("73754150", "01")) {
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }


        //주식잔고 조회
        b3!!.setOnClickListener {
            RetrofitManager.instance.getInquireBalance(
                "Bearer "+db!!.TokenTimeDao().getToken(),
                InquireBalance(
                    "73754150",
                    "01",
                    "N",
                    "",
                    "01",
                    "01",
                    "N",
                    "N",
                    "01",
                    "",
                    "")
            ) {
                var str = ""
                it.forEach {
                    str += "종목명 : " + it.PRDT_NAME+"\n"
                    str += "매입개수 : " + it.HLDG_QTY+"\n"
                    str += "평가금액 : " + it.EVLU_AMT+"\n"
                }
                mTextView!!.text = str
            }
        }

        //매수가능금액 조회
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
        }

    }
}