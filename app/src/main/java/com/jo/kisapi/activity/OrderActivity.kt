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
import com.jo.kisapi.AlarmReceiver
import com.jo.kisapi.R
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityOrderBinding
import com.jo.kisapi.viewmodel.OrderViewModel
import java.util.*

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    var pdno: String = ""

    private val viewModel: OrderViewModel by viewModels {
        OrderViewModel.Factory((application as KISApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val items = arrayOf("삼성전자","카카오","기아","HMM","아아윈플러스")
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        viewModel.getCash()

        binding.spinner.adapter = myAdapter
        binding.spinner.setSelection(0)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        pdno = "005930"
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
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        binding.test4.setOnClickListener {

            viewModel.orderBuy(pdno, viewModel.count.value.toString())
            if (viewModel.rt_cd.value == "0") {
                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                intent.putExtra("pdno", pdno)
                intent.putExtra("count", viewModel.count.value.toString())
                val pendingIntent = PendingIntent.getBroadcast(
                    this, AlarmReceiver.NOTIFICATION_ID, intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 15)
                    set(Calendar.MINUTE, 10)
                }

                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
                Log.d("TAG", "toastMessage")

            }
        }

        viewModel.msg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        })

    }
}