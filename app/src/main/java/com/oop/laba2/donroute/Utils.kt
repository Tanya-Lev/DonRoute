package com.oop.laba2.donroute

import android.content.res.Resources
import com.oop.laba2.donroute.enteties.TransportType

object Utils {

        val Int.dp: Int
            get() = (this / Resources.getSystem().displayMetrics.density).toInt()

        val Int.px: Int
            get() = (this * Resources.getSystem().displayMetrics.density).toInt()


    fun getBackgroundFromTransportType(type:TransportType):Int{
        return when(type){
            TransportType.BUS -> R.drawable.rectangle_rounded_bus_stroked
            TransportType.TROLLEYBUS -> R.drawable.rectangle_rounded_trolleybus_stroked
            TransportType.TRAM -> R.drawable.rectangle_rounded_tram_stroked
        }
    }
    object EmailValidationService {
        private var emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        fun isValidEmail(email: String): Boolean {
            return email.trim { it <= ' '}.matches(emailPattern.toRegex())
        }
    }
}