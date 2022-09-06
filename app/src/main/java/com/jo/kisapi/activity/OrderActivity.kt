package com.jo.kisapi.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.jo.kisapi.AlarmReceiver
import com.jo.kisapi.R
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityOrderBinding
import com.jo.kisapi.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    var pdno: String = ""
    var auto = false
    private val viewModel: OrderViewModel by viewModels {
        OrderViewModel.Factory((application as KISApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val items = arrayOf("두산에너빌리티","카카오","기아","HMM","아아윈플러스","KT")
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        viewModel.getCash()

        lifecycleScope.launch {
            while (true) {
                delay(500)
                viewModel.getCurrentPrice(pdno)
            }
        }
        binding.spinner.adapter = myAdapter
        binding.spinner.setSelection(0)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        pdno = "034020"
                        viewModel.getCurrentPrice(pdno)
                        viewModel.getTargetPrice(pdno)
                    }
                    1 -> {
                        pdno = "035720"
                        viewModel.getCurrentPrice(pdno)
                        viewModel.getTargetPrice(pdno)
                    }
                    2 -> {
                        pdno = "000270"
                        viewModel.getCurrentPrice(pdno)
                        viewModel.getTargetPrice(pdno)
                    }
                    3 -> {
                        pdno = "011200"
                        viewModel.getCurrentPrice(pdno)
                        viewModel.getTargetPrice(pdno)
                    }
                    4 -> {
                        pdno = "123010"
                        viewModel.getCurrentPrice(pdno)
                        viewModel.getTargetPrice(pdno)
                    }
                    5 -> {
                        pdno = "030200"
                        viewModel.getCurrentPrice(pdno)
                        viewModel.getTargetPrice(pdno)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        binding.test4.setOnClickListener {
            if (viewModel.count.value!!.toInt() * viewModel.targetPrice.value!!.toInt() <= viewModel.cashes.value!!) {

                if (!viewModel.auto.value!!) {

                    binding.test4.text = "취소"
                    viewModel.auto.value = true
                    setEnable(false)
                    viewModel.auto(pdno)

                } else {
                    setEnable(true)
                    viewModel.auto.value = false
                    binding.test4.text = "주문"
                }
            }else{

                Toast.makeText(this,"보유금액이 부족합니다",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.auto.observe(this, {
            setEnable(!auto)
        })

        viewModel.msg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        })

    }

    private fun setEnable(b: Boolean) {
        binding.spinner.isEnabled = b
        binding.minus.isEnabled = b
        binding.plus.isEnabled = b
        binding.edit.isEnabled = b

    }
}