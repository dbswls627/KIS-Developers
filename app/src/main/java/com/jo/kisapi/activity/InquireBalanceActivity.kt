package com.jo.kisapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jo.kisapi.R
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityInquireBalanceBinding
import com.jo.kisapi.viewmodel.InquireBalanceViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InquireBalanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInquireBalanceBinding

    private val viewModel: InquireBalanceViewModel by viewModels {
        InquireBalanceViewModel.Factory((application as KISApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquire_balance)
        binding.lifecycleOwner = this
        binding.inquireBalanceViewModel = viewModel
        binding.re!!.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {

            viewModel.getInquireBalance()
            viewModel.getCash()

        }
            lifecycleScope.launch {
                while(true) {
                    delay(1000)
                    viewModel.getInquireBalance()
                }
            }
    }
}

