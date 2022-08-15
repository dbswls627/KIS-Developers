package com.jo.kisapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jo.kisapi.R
import com.jo.kisapi.output1
import java.util.*


class Adapter(var list: LiveData<List<output1>> ) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null

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
        holder .pchs_avg_pric.text = list.value!![position].pchs_avg_pric   //매입평균금액
        holder .prpr.text = list.value!![position].prpr                     //현재가
        holder .hldg_qty.text = list.value!![position].hldg_qty             //보유수량
        holder .pchs_amt.text = list.value!![position].pchs_amt             //매입금액
        holder .evlu_amt.text = list.value!![position].evlu_amt             //평가금액
        holder .evlu_pfls_amt.text = list.value!![position].evlu_pfls_amt   //평가손익금액
        holder .evlu_pfls_rt.text = list.value!![position].evlu_pfls_rt     //평가손익률
    }

    override fun getItemCount(): Int {
        return try {
            list.value!!.size
        }catch (e:Exception){
            0
        }
    }
}

