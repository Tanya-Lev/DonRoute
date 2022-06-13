package com.oop.laba2.donroute.enteties

enum class TransportType{
    BUS,
    TROLLEYBUS,
    TRAM
}

data class Transport(
    val id:Int? = null,
    val timestamp:String? =null,
    val number:String? = null,
    val tuda: List<String>? = null,
    val obratno: List<String>? = null,
    var _isFavorite:Boolean = false,
    val transportType:TransportType = TransportType.BUS
)

