package com.oop.laba2.donroute.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oop.laba2.donroute.network.NetWorkAPI.getAllTransport
import com.oop.laba2.donroute.network.NetWorkAPI.initCacheSaving
import com.oop.laba2.donroute.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DonRoute)
        super.onCreate(savedInstanceState)

//        val buffer = BufferedReader(InputStreamReader(assets.open("allTransportBase.json")))

//        val qwe = buffer.readText()
//        allTransport = Klaxon().parseArray<Transport>(qwe)!!
//        updateAllTransport()
        getAllTransport()
        initCacheSaving()
        val intent = Intent(this, ChooseTransportTypeActivity::class.java)
        startActivity(intent)
        finish()
    }
}