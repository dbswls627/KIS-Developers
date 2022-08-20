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
    fun setTextColor2(textView: TextView, float: Float) {
        if (float > 0 )         textView.setTextColor(Color.RED)
        else if (float < 0 )         textView.setTextColor(Color.BLUE)
        textView.text = "$float %"

    }

    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextColor3")
    fun setTextColor3(textView: TextView, int: Int) {
        when {
            int > 0 -> textView.setTextColor(Color.RED)
            int < 0 -> textView.setTextColor(Color.BLUE)
            else -> textView.setTextColor(Color.BLACK)
        }
        textView.text = DecimalFormat("#,###").format(int)

    }
}
