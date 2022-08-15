package com.jo.kisapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jo.kisapi.R
import com.jo.kisapi.adapter.Adapter
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityInquireBalanceBinding
import com.jo.kisapi.output1
import com.jo.kisapi.viewmodel.InquireBalanceViewModel

class InquireBalanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInquireBalanceBinding // xml 파일명이 CamelCase 표기로 바뀌고 Binding이 붙습니다.

    private val viewModel: InquireBalanceViewModel by viewModels {
        InquireBalanceViewModel.Factory((application as KISApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquire_balance)
        binding.lifecycleOwner = this
        binding.re!!.layoutManager = LinearLayoutManager(this)

        viewModel.getInquireBalance()
        viewModel.output1List.observe(this, {
            binding.re!!.adapter = Adapter(viewModel.output1List)
            binding.dncaTotAmt!!.text = viewModel.output2.value!!.dnca_tot_amt

            /*var  sumPchsAmt = 0
            (viewModel.output1List as ArrayList<output1>).forEach {
                sumPchsAmt += it.pchs_amt.toInt()
            }*/

        })
    }
}

