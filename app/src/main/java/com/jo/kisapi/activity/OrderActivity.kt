package com.jo.kisapi.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.jo.kisapi.R
import com.jo.kisapi.databinding.ActivityOrderBinding
import com.jo.kisapi.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private val viewModel: OrderViewModel by viewModels()

    var longPosition: String = "069500"
    var shortPosition: String = "114800"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getCash()
        viewModel.getLongTargetPrice(longPosition)
        viewModel.getShortTargetPrice(shortPosition)
        viewModel.getLongCurrentPrice(longPosition)
        viewModel.getShortCurrentPrice(shortPosition)



        binding.order.setOnClickListener {
            //주문가능금액이 더 많을 시 시작
            try {
                if ((viewModel.longMax.value!! * viewModel.longCount.value!!.toInt() <= viewModel.cashes.value!!) &&
                    (viewModel.shortMax.value!! * viewModel.shortCount.value!!.toInt() <= viewModel.cashes.value!!)
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
            }
        }
        viewModel.auto.observe(this, {

            if (it) {
                binding.order.progress = 50
                binding.order.text = "취소"
                setEnable(false)
            } else {
                binding.order.progress = 0
                binding.order.text = "주문"
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