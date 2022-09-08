package com.jo.kisapi.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.jo.kisapi.R
import com.jo.kisapi.application.KISApplication
import com.jo.kisapi.databinding.ActivityOrderBinding
import com.jo.kisapi.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    var longPosition: String = "069500"
    var shortPosition: String = "114800"
    var auto = false
    private val viewModel: OrderViewModel by viewModels {
        OrderViewModel.Factory((application as KISApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getCash()
        viewModel.getLongTargetPrice(longPosition)
        viewModel.getShortTargetPrice(shortPosition)

        lifecycleScope.launch {
            while (true) {
                viewModel.getLongCurrentPrice(longPosition)
                viewModel.getShortCurrentPrice(shortPosition)
                delay(500)
            }
        }


        binding.test4.setOnClickListener {
            //주문가능금액이 더 많을 시
            try {
                if ((viewModel.longCount.value!!.toInt() * viewModel.longTargetPrice.value!!.toInt() <= viewModel.cashes.value!!) &&
                    (viewModel.shortCount.value!!.toInt() * viewModel.shortTargetPrice.value!!.toInt() <= viewModel.cashes.value!!)
                ) {
                    //주문 시작
                    if (!viewModel.auto.value!!) {

                        viewModel.auto.value = true
                        setEnable(false)

                        viewModel.longAuto(longPosition)
                        viewModel.shortAuto(shortPosition)

                        //주문 취소
                    } else {
                        viewModel.auto.value = false
                        setEnable(true)

                    }
                } else {
                    Toast.makeText(this, "보유금액이 부족합니다", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                //count = 0

            }
        }
        viewModel.auto.observe(this, {

            if (it) {
                binding.test4.text = "취소"
                setEnable(false)
            } else {
                binding.test4.text = "주문"
                setEnable(true)
            }
        })

        viewModel.msg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        })

    }

    private fun setEnable(b: Boolean) {
        binding.minus.isEnabled = b
        binding.plus.isEnabled = b
        binding.edit.isEnabled = b
        binding.minus2.isEnabled = b
        binding.plus2.isEnabled = b
        binding.edit2.isEnabled = b

    }
}