package com.oop.laba2.donroute.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oop.laba2.donroute.AllTransport
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.adapters.StationAdapter

class ChooseStationActivity : AppCompatActivity() {

    var isLogined = false

    val allStationSet = HashSet<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_station)

        val favouritesFAB = findViewById<FloatingActionButton>(R.id.favouritesFAB)
        val chooseStationRV = findViewById<RecyclerView>(R.id.chooseStationRV)

        val auth = Firebase.auth
        if(auth.uid!=null) {
            isLogined = true}

        findViewById<ImageView>(R.id.backIV).setOnClickListener {
            finish()
        }

        favouritesFAB.setOnClickListener {
            val intent = Intent(this,TransportNumbersActivity::class.java)

            if(isLogined) {
                intent.putExtra("layoutState", 3)
                startActivity(intent)
            }else{
                startActivity(Intent(this,AddFavoritesErrorActivity::class.java))
            }
        }
        filterAutoStation()

        val adapter = StationAdapter(allStationSet.toList())
        chooseStationRV.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        chooseStationRV.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun filterAutoStation(){
        AllTransport.allTransport.forEach{
            allStationSet.add(it.tuda!!.first())
            allStationSet.add(it.tuda.last())
        }
    }
}