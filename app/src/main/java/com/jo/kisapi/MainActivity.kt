package com.jo.kisapi

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var b: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b = findViewById(R.id.button)
        b2 = findViewById(R.id.button2)
        b3 = findViewById(R.id.button3)

        b!!.setOnClickListener {
            RetrofitManager.instance.getHashKey(HashKey("73754150", "01")) {
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }

        b2!!.setOnClickListener {
            RetrofitManager.instance.getToken(
                TokenHeader(
                    "client_credentials",
                    appkey = Util.API_KEY,
                    Util.API_KEY_SECRET
                )
            ) {
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }

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