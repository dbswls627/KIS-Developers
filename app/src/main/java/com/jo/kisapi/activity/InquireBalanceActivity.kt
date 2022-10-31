package com.jo.kisapi.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jo.kisapi.R
import com.jo.kisapi.databinding.ActivityInquireBalanceBinding
import com.jo.kisapi.viewmodel.InquireBalanceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquireBalanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInquireBalanceBinding

    private val viewModel: InquireBalanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquire_balance)
        binding.lifecycleOwner = this
        binding.inquireBalanceViewModel = viewModel
        binding.re!!.layoutManager = LinearLayoutManager(this)


        viewModel.getInquireBalance()
        viewModel.getCash()
        viewModel.getInquireBalance()


    }
}

