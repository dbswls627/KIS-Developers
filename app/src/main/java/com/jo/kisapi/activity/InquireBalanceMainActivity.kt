package com.jo.kisapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jo.kisapi.AppDatabase
import com.jo.kisapi.R

class InquireBalanceMainActivity : AppCompatActivity() {

    private lateinit var db :AppDatabase
    var re: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquire_balance_main)

        re = findViewById(R.id.re)
        db = AppDatabase.getInstance(this)!!
        re!!.layoutManager = LinearLayoutManager(this)

       /* RetrofitManager.instance.getInquireBalance(
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
            re!!.adapter = Adapter(it)
        }*/
    }
}