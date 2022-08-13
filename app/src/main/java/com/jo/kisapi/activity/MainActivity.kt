package com.jo.kisapi.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jo.kisapi.*


class MainActivity : AppCompatActivity() {

    private var b: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var mTextView: TextView? = null
    var db: AppDatabase? = null
    private lateinit var viewModelFactory: MyViewModel.Factory
    private lateinit var viewModel: MyViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tokenTimeDao: TokenTimeDao
        viewModelFactory = MyViewModel.Factory(Repository(this.application))
        viewModel = ViewModelProvider(this,viewModelFactory)[MyViewModel::class.java]

        b = findViewById(R.id.button)
        b2 = findViewById(R.id.button2)
        b3 = findViewById(R.id.button3)
        b4 = findViewById(R.id.button4)
        mTextView = findViewById(R.id.text)


        //토큰 갱신
        viewModel.getTokenCheck()
        /*  //해쉬
        b!!.setOnClickListener {
            RetrofitManager.instance.getHashKey(HashKey("73754150", "01")) {
                Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show()
            }
        }


        //주식잔고 조회
        b3!!.setOnClickListener {
            val intent = Intent(this,InquireBalanceMainActivity::class.java)
            startActivity(intent)
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

    }*/
    }

}