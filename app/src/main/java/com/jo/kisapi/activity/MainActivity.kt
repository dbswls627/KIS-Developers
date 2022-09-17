package com.jo.kisapi.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jo.kisapi.R
import com.jo.kisapi.databinding.ActivityMainBinding
import com.jo.kisapi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //토큰 갱신
        viewModel.getTokenCheck()
        viewModel.changeRefresh()

        binding.button3!!.setOnClickListener {
            val intent = Intent(this, InquireBalanceActivity::class.java)
            startActivity(intent)
        }

        binding.autoTrading!!.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        binding.autoTrading2!!.setOnClickListener {
            val intent = Intent(this, AutoTradingActivity::class.java)
            startActivity(intent)
        }



        viewModel.msg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }



}