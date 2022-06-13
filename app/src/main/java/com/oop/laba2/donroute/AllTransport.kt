package com.oop.laba2.donroute

import com.oop.laba2.donroute.enteties.Transport
import com.oop.laba2.donroute.enteties.TransportType

object AllTransport {
    var allTransport: List<Transport> = listOf()

    val favoriteTransport:List<Transport>
        get() {return allTransport.filter { it._isFavorite?:false }}

// searchStopsNonFilteredList
    val allStops:List<String>
        get(){
            val stopSet = HashSet<String>()

            allTransport.forEach {
                stopSet.addAll(it.tuda!!)
                stopSet.addAll(it.obratno!!)
            }
            return stopSet.toList()
        }


    data class FavoriteTransport(
        val number : String,
        val type : TransportType
    )
}