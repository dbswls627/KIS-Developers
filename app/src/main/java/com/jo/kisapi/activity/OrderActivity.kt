package com.jo.kisapi.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.jo.kisapi.R
import com.jo.kisapi.Util
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityOrderBinding
import com.jo.kisapi.retrofit.IRetrofit
import com.jo.kisapi.retrofit.RetrofitClient
import com.jo.kisapi.viewmodel.MyViewModel
import com.jo.kisapi.viewmodel.OrderViewModel

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding

    private val viewModel: OrderViewModel by viewModels {
        OrderViewModel.Factory((application as KISApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val items = arrayOf("삼성전자","카카오","기아","HMM")
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        binding.spinner.adapter = myAdapter
        binding.spinner.setSelection(0)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                binding.viewModel
                when(position) {
                    0   ->  {
                        viewModel.getCurrentPrice("005930")
                        viewModel.getTargetPrice("005930")
                    }
                    1   ->  {
                        viewModel.getCurrentPrice("035720")
                        viewModel.getTargetPrice("035720")
                    }
                    2 -> {
                        viewModel.getCurrentPrice("000270")
                        viewModel.getTargetPrice("000270")
                    }
                    3 -> {
                        viewModel.getCurrentPrice("011200")
                        viewModel.getTargetPrice("011200")
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}