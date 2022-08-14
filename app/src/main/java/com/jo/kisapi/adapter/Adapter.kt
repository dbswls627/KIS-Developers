package com.jo.kisapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jo.kisapi.R
import com.jo.kisapi.output1
import java.util.*


class Adapter(var list: ArrayList<output1> ) :

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
        (holder as BalanaceList).prdt_name.text = list[position].PRDT_NAME
        holder .hldg_qty.text = list[position].HLDG_QTY
        holder .pchs_avg_pric.text = list[position].PCHS_AVG_PRIC
        holder .prpr.text = list[position].PRPR
        holder .pchs_amt.text = list[position].PCHS_AMT
        holder .evlu_amt.text = list[position].EVLU_AMT
        holder .evlu_pfls_amt.text = list[position].EVLU_PFLS_AMT
        holder .evlu_pfls_rt.text = list[position].EVLU_PFLS_RT
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

