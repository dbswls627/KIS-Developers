package com.jo.kisapi.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jo.kisapi.*
import com.jo.kisapi.retrofit.RetrofitManager

class MainActivity : AppCompatActivity() {

    private var b: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var db : AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)

        b = findViewById(R.id.button)
        b2 = findViewById(R.id.button2)
        b3 = findViewById(R.id.button3)

        //해쉬
        b!!.setOnClickListener {
            RetrofitManager.instance.getHashKey(HashKey("73754150", "01")) {
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }

        //토큰
        b2!!.setOnClickListener {
            RetrofitManager.instance.getToken(
                TokenHeader(
                    "client_credentials",
                    appkey = Util.API_KEY,
                    Util.API_KEY_SECRET
                )
            ) {
                db?.TokenTimeDao()!!.insert(TokenTime("Bearer",it,"time"))
            }
        }

        //주식잔고
        b3!!.setOnClickListener {
            RetrofitManager.instance.getInquireBalance(
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
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }

    }
}