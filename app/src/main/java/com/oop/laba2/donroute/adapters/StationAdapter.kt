package com.oop.laba2.donroute.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.enteties.Transport
import com.oop.laba2.donroute.ui.RouteActivity
import com.oop.laba2.donroute.ui.TransportNumbersActivity


class StationAdapter(var stationList: List<String>): RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    init {
        stationList = stationList.sorted()
    }
    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val stationBtn: TextView = itemView.findViewById(R.id.stationBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.choose_station_item, parent, false)
        return StationAdapter.StationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.stationBtn.text = stationList[position]
        holder.stationBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context,TransportNumbersActivity::class.java)
            intent.putExtra("station",stationList[position])
            intent.putExtra("layoutState",4)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return stationList.size
    }


}