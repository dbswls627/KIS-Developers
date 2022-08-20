package com.jo.kisapi.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jo.kisapi.R
import com.jo.kisapi.dataModel.output1
import java.text.DecimalFormat
import java.util.*


class Adapter(var list: MutableLiveData<List<output1>> ) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null
    val dec = DecimalFormat("#,###")
    inner class BalanaceList(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var prdt_name: TextView = itemView.findViewById<View>(R.id.prdt_name) as TextView
        var hldg_qty: TextView = itemView.findViewById<View>(R.id.hldg_qty) as TextView
        var pchs_avg_pric: TextView = itemView.findViewById<View>(R.id.pchs_avg_pric) as TextView
        var prpr: TextView = itemView.findViewById<View>(R.id.prpr) as TextView
        var pchs_amt: TextView = itemView.findViewById<View>(R.id.pchs_amt) as TextView
        var evlu_amt: TextView = itemView.findViewById<View>(R.id.evlu_amt) as TextView
        var evlu_pfls_amt: TextView = itemView.findViewById<View>(R.id.evlu_pfls_amt) as TextView
        var evlu_pfls_rt: TextView = itemView.findViewById<View>(R.id.evlu_pfls_rt) as TextView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return BalanaceList(LayoutInflater.from(parent.context).inflate(R.layout.balaceitem, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BalanaceList).prdt_name.text = list.value!![position].prdt_name
        holder .pchs_avg_pric.text = dec.format(list.value!![position].pchs_avg_pric)  //매입평균금액
        holder .prpr.text = dec.format(list.value!![position].prpr)                     //현재가
        holder .hldg_qty.text = dec.format(list.value!![position].hldg_qty)         //보유수량
        holder .pchs_amt.text = dec.format(list.value!![position].pchs_amt)            //매입금액
        holder .evlu_amt.text = dec.format(list.value!![position].evlu_amt)           //평가금액
        holder .evlu_pfls_amt.text = dec.format(list.value!![position].evlu_pfls_amt)  //평가손익금액
        holder .evlu_pfls_rt.text = list.value!![position].evlu_pfls_rt.toString()  + "%"   //평가손익률

        if( list.value!![position].evlu_pfls_amt.toInt() > 0){
            holder.evlu_pfls_amt.setTextColor(Color.RED)
            holder.evlu_pfls_rt.setTextColor(Color.RED)
        } else{
            holder.evlu_pfls_amt.setTextColor(Color.BLUE)
            holder.evlu_pfls_rt.setTextColor(Color.BLUE)
        }
    }

    override fun getItemCount(): Int {
        return try {
            list.value!!.size
        }catch (e:Exception){
            0
        }
    }
}

