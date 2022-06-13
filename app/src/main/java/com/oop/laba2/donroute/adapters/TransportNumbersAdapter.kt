package com.oop.laba2.donroute.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.Utils.getBackgroundFromTransportType
import com.oop.laba2.donroute.enteties.Transport
import com.oop.laba2.donroute.enteties.TransportType
import com.oop.laba2.donroute.ui.RouteActivity


class TransportNumbersAdapter(var transportList: List<Transport>) :
    RecyclerView.Adapter<TransportNumbersAdapter.TransportViewHolder>() {

    init {
        this.transportList = this.transportList.sortedBy { it.number!!.replace(Regex("[^0-9]"), "").toInt()}
    }

    var isReverse = false



    class TransportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val numberTV: TextView = itemView.findViewById(R.id.numberTV)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transport_numbers_item, parent, false)
        return TransportNumbersAdapter.TransportViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {

        holder.numberTV.setBackgroundResource(getBackgroundFromTransportType(transportList[0].transportType!!))
        holder.numberTV.text = transportList[position].number.toString()
        holder.numberTV.setOnClickListener {
            val intent = Intent(holder.itemView.context,RouteActivity::class.java)
            intent.putExtra("timestamp",transportList[position].timestamp)
            intent.putExtra("transportNumber",transportList[position].number)
            intent.putExtra("transportType",transportList[position].transportType!!.name)
            intent.putExtra("transportId",transportList[position].id)
            intent.putExtra("routeTuda",transportList[position].tuda!!.toTypedArray())
            intent.putExtra("routeObratno",transportList[position].obratno!!.toTypedArray())
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return transportList.size
    }


}
