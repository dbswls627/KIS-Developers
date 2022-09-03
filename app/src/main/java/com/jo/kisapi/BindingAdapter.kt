package com.jo.kisapi

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
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


    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextColor1")
    fun setTextColor1(textView: TextView, int: Int) {
        if (int > 0 )         textView.setTextColor(Color.RED)
        else if (int < 0 )         textView.setTextColor(Color.BLUE)
        textView.text = DecimalFormat("#,###").format(int)

    }

    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextColor2")
    fun setTextColor2(textView: TextView, double: Double) {
        if (double > 0) textView.setTextColor(Color.RED)
        else if (double < 0) textView.setTextColor(Color.BLUE)
        textView.text = "$double %"

    }

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

    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("ydPrice", "target")
    fun setTextColor4(textView: TextView, ydPrice: Int, target: Int) {
        textView.setTextColor(Color.RED)
        textView.text = DecimalFormat("+#,###").format(target - ydPrice)
    }

    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter( "target1", "ydPrice")
    fun setTextColor5(textView: TextView,  target1: Int, ydPrice :Int) {
        textView.setTextColor(Color.RED)
        textView.text = "+"+DecimalFormat("##.##").format(((target1 - ydPrice)* 100 / ydPrice.toDouble()))+"%"
    }

    @JvmStatic
    @BindingAdapter( "count", "target")
    fun setOrderAmount(textView: TextView, count :String,target: Int) {
        if (count.isNotEmpty()){
            textView.text = (target * count.toInt()).toString()
        }
    }
}

