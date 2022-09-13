package com.jo.kisapi.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jo.kisapi.R
import com.jo.kisapi.databinding.ActivityAutoTradingBinding
import com.jo.kisapi.viewmodel.AutoTradingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class AutoTradingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAutoTradingBinding
    private val viewModel: AutoTradingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auto_trading)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getTimePrice("069500")
        viewModel.getStarPrice("069500")

    }
}