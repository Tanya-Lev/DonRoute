package com.oop.laba2.donroute.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.customView.RouteCustomView
import kotlin.coroutines.coroutineContext

import androidx.core.content.res.ResourcesCompat

import android.graphics.Typeface




class RouteAdapter(val routeListTuda: List<String>,val routeListObratno: List<String>):RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {
    private var isReverse = false
    var routeList = listOf<String>()

    fun reverse(){
        isReverse = !isReverse

        routeList = if(isReverse)
            routeListObratno
        else
            routeListTuda

        notifyDataSetChanged()
    }

    class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val routeItemName: TextView = itemView.findViewById(R.id.routeItemName)
        val routeCustomView: RouteCustomView = itemView.findViewById(R.id.routeCustomView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        routeList = if(isReverse)
            routeListObratno
        else
            routeListTuda


        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.route_item, parent, false)
        return RouteAdapter.RouteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {

            holder.routeItemName.text =  routeList[position]


        when(position){
            0->{
                val typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.roboto_medium)
                holder.routeItemName.textSize = 20F
                holder.routeItemName.typeface = typeface
                if(!isReverse)
                    holder.routeCustomView.circleColor =  holder.itemView.context.resources.getColor(R.color.green)
                else
                    holder.routeCustomView.circleColor =  holder.itemView.context.resources.getColor(R.color.red)

                holder.routeCustomView.state = 0

            }
            routeList.size-1 -> {
                val typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.roboto_medium)
                holder.routeItemName.textSize = 20F
                holder.routeItemName.typeface = typeface
                if(isReverse)
                    holder.routeCustomView.circleColor =  holder.itemView.context.resources.getColor(R.color.green)
                else
                    holder.routeCustomView.circleColor =  holder.itemView.context.resources.getColor(R.color.red)

                holder.routeCustomView.state = 2
            }
            else -> {
                val typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.roboto)
                holder.routeItemName.textSize = 16F
                holder.routeItemName.typeface = typeface
                holder.routeCustomView.state = 1
            }
        }


    }

    override fun getItemCount(): Int {
        return if(!isReverse)
            routeListTuda.size
        else
            routeListObratno.size
    }
}