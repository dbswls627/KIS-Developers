package com.jo.kisapi

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jo.kisapi.adapter.Adapter
import com.jo.kisapi.dataModel.output1
import java.text.DecimalFormat

object BindingAdapter {
    //Recyclerview
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(recyclerView: RecyclerView, items: MutableLiveData<List<output1>>) {
        if (recyclerView.adapter == null) {

            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            val adapter = Adapter(items)
            recyclerView.adapter = adapter

        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    //Int 부호의 따른 color
    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextColor1")
    fun setTextColor1(textView: TextView, int: Int) {
        if (int > 0 )         textView.setTextColor(Color.RED)
        else if (int < 0 )         textView.setTextColor(Color.BLUE)
        textView.text = DecimalFormat("#,###").format(int)

    }

    // double 부호의 따른 color
    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextColor2")
    fun setTextColor2(textView: TextView, double: Double) {
        if (double > 0) textView.setTextColor(Color.RED)
        else if (double < 0) textView.setTextColor(Color.BLUE)
        textView.text = "$double %"

    }

    //change(Int)의 부호에 따른 color
    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("amt", "change")
    fun setTextColor3(textView: TextView, amt: Int, change: Int) {
        when {
            change > 0 -> textView.setTextColor(Color.RED)
            change < 0 -> textView.setTextColor(Color.BLUE)
            else -> textView.setTextColor(Color.BLACK)
        }
        textView.text = DecimalFormat("#,###").format(amt)

    }

    //전일가격과 타겟가격의 대비값
    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("ydPrice", "target")
    fun setTextColor4(textView: TextView, ydPrice: Int, target: Int) {
        if (target - ydPrice > 0) {
            textView.setTextColor(Color.RED)
            textView.text = DecimalFormat("+#,###").format(target - ydPrice)

        } else {
            textView.setTextColor(Color.BLUE)
            textView.text = DecimalFormat("#,###").format(target - ydPrice)

        }
    }

    //전일가격과 타겟가격의 대비 %
    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter( "target1", "ydPrice")
    fun setTextColor5(textView: TextView,  target1: Int, ydPrice :Int) {
        if (target1 - ydPrice > 0) {
            textView.setTextColor(Color.RED)
            textView.text =
                DecimalFormat("+##.##").format(((target1 - ydPrice) * 100 / ydPrice.toDouble())) + "%"
        } else {
            textView.setTextColor(Color.BLUE)
            textView.text =
                DecimalFormat("##.##").format(((target1 - ydPrice) * 100 / ydPrice.toDouble())) + "%"
        }

    }

    //전일가격과 타겟가격의 차이 따른 color
    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("target2", "ydPrice")
    fun setTextColor6(textView: TextView, target2: Int, ydPrice: Int) {
        if (target2 - ydPrice > 0) {
            textView.setTextColor(Color.RED)
            textView.text = DecimalFormat("#,###").format(target2)
        } else {
            textView.setTextColor(Color.BLUE)
            textView.text = DecimalFormat("#,###").format(target2)
        }

    }

    @JvmStatic
    @BindingAdapter("count", "target")
    fun setOrderAmount(textView: TextView, count: String, target: Int) {
        if (count.isNotEmpty()) {
            textView.text = DecimalFormat("#,###원").format((target * count.toInt()))
        }
    }


}

