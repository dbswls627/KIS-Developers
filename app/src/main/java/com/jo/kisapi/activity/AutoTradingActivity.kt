package com.jo.kisapi.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jo.kisapi.AlarmReceiver
import com.jo.kisapi.R
import com.jo.kisapi.databinding.ActivityAutoTradingBinding
import com.jo.kisapi.viewmodel.AutoTradingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AutoTradingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAutoTradingBinding
    private val viewModel: AutoTradingViewModel by viewModels()
    var pdno = ""
    var count = ""
    var amt = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auto_trading)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getCash()

        viewModel.getLongTimePrice("069500")
        viewModel.getLongStarPrice("069500")

        viewModel.getShortTimePrice("114800")
        viewModel.getShortStarPrice("114800")

        binding.order.setOnClickListener {
            if (viewModel.longStartPrice.value!! < viewModel.longTimePrice.value!!) {
                pdno = "069500"
                count = viewModel.longCount.value!!
                amt = (viewModel.longStartPrice.value!! + 1000).toString()

            } else if (viewModel.shortStartPrice.value!! < viewModel.shortTimePrice.value!!) {
                pdno = "114800"
                count = viewModel.shortCount.value!!
                amt = (viewModel.shortStartPrice.value!! + 1000).toString()

            }
            if (pdno != "") {
                Toast.makeText(this, "주문전송 완료", Toast.LENGTH_SHORT).show()
                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                intent.putExtra("pdno", pdno)
                intent.putExtra("amt", amt)
                intent.putExtra("count", count)
                val pendingIntent = PendingIntent.getBroadcast(
                    this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 15)
                    set(Calendar.MINUTE, 0)
                }

                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        }
    }
}