package com.oop.laba2.donroute.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oop.laba2.donroute.AllTransport
import com.oop.laba2.donroute.network.NetWorkAPI
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.Utils.getBackgroundFromTransportType
import com.oop.laba2.donroute.Utils.px
import com.oop.laba2.donroute.adapters.RouteAdapter
import com.oop.laba2.donroute.enteties.TransportType

class RouteActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var isLogined = false

    var tuda = listOf(
        "остановка1",
        "остановка2",
        "остановка3",
        "остановка4",
        "остановка5",
    )
    var obratno = listOf(
        "остановка1",
        "остановка2",
        "остановка3",
        "остановка4",
        "остановка5",
    )

    var isReversed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        auth = Firebase.auth

        val routeResView = findViewById<RecyclerView>(R.id.routeResView)
        val reverseIV = findViewById<ImageView>(R.id.reverseIV)
        val favouritesFAB = findViewById<FloatingActionButton>(R.id.favouritesFAB)
        val busIconIV = findViewById<ImageView>(R.id.busIconIV)
        val stationTudaTV = findViewById<TextView>(R.id.stationTudaTV)
        val stationObratnoTV = findViewById<TextView>(R.id.stationObratnoTV)
        val transportNumber = findViewById<TextView>(R.id.transportNumber)
        val stopsNumTV = findViewById<TextView>(R.id.stopsNumTV)
        val addFavoritesIV = findViewById<ImageView>(R.id.addFavoritesIV)
        val timestampTV = findViewById<TextView>(R.id.timestampTV)

        if(auth.uid!=null) {
            isLogined = true}

        busIconIV.setImageResource(
            when(TransportType.valueOf(intent.getStringExtra("transportType")?:"BUS")){
                TransportType.BUS -> R.drawable.ic_bus
                TransportType.TROLLEYBUS -> R.drawable.ic_trolleybus
                TransportType.TRAM -> R.drawable.ic_tram
            }
        )

        timestampTV.text = intent.getStringExtra("timestamp")
        transportNumber.text = intent.getStringExtra("transportNumber")!!.trim()
        val transportId = intent.getIntExtra("transportId",0)

        transportNumber.setBackgroundResource(
            getBackgroundFromTransportType(
                TransportType.valueOf(intent.getStringExtra("transportType")?:"BUS")
            )
        )
        //transportType
        AllTransport.allTransport.find { it.id == transportId }.also {
            if(it!!._isFavorite) {
                addFavoritesIV.setImageResource(R.drawable.ic_red_heart)
            }else{
                addFavoritesIV.setImageResource(R.drawable.ic_heart_small)
            }
        }


        addFavoritesIV.setOnClickListener {

            if(auth.uid!=null) {
                AllTransport.allTransport.find { it.id == transportId }.also {
                    it!!._isFavorite = !it._isFavorite
                    if (it._isFavorite) {
                        //Toast.makeText(this, "Добавленно в избранное", Toast.LENGTH_SHORT).show()
                        NetWorkAPI.addToFavorite(
                            auth.uid!!,
                            AllTransport.FavoriteTransport(it.number!!, it.transportType!!)
                        )

                        Snackbar.make(
                            addFavoritesIV,
                            "Маршрут добавлен в Избранное",
                            Snackbar.LENGTH_SHORT
                        )
                            .setBackgroundTint(getColor(R.color.white))
                            .also {
                                it.setTextColor(getColor(R.color.black))
                                val layout = it.view
                                val textView =
                                    layout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                                textView.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.ic_done,
                                    0,
                                    0,
                                    0
                                )
                                textView.compoundDrawablePadding = 14.px
                                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                            }.show()
                        addFavoritesIV.setImageResource(R.drawable.ic_red_heart)
                    } else {
                        NetWorkAPI.removeFromFavorite(
                            auth.uid!!,
                            AllTransport.FavoriteTransport(it.number!!, it.transportType!!)
                        )
                        Snackbar.make(
                            addFavoritesIV,
                            "Маршрут убран из Избранного",
                            Snackbar.LENGTH_SHORT
                        )
                            .setBackgroundTint(getColor(R.color.white))
                            .also {
                                it.setTextColor(getColor(R.color.black))
                                val layout = it.view
                                val textView =
                                    layout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                                textView.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.ic_done,
                                    0,
                                    0,
                                    0
                                )
                                textView.compoundDrawablePadding = 14.px
                                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                            }.show()
                        addFavoritesIV.setImageResource(R.drawable.ic_heart_small)
                    }
                }
            }else{
                Toast.makeText(this, "Вы не авторизованы!", Toast.LENGTH_SHORT).show()
            }
        }


        favouritesFAB.setOnClickListener {
            val intent = Intent(this,TransportNumbersActivity::class.java)
//            intent.putExtra("layoutState",3)
//            startActivity(intent)

            if(isLogined) {
                intent.putExtra("layoutState", 3)
                startActivity(intent)
            }else{
                startActivity(Intent(this,AddFavoritesErrorActivity::class.java))
            }

        }
        findViewById<ImageView>(R.id.backIV).setOnClickListener {
            startActivity(Intent(this,ChooseTransportTypeActivity::class.java).also{it.setFlags(FLAG_ACTIVITY_CLEAR_TASK)})
        }
        if(intent.hasExtra("routeTuda") && intent.hasExtra("routeObratno")) {
            tuda = intent.getStringArrayExtra("routeTuda")!!.toList()
            obratno = intent.getStringArrayExtra("routeObratno")!!.toList()
        }
         //else Throw   !!!!!!!!!!
        stationTudaTV.text = tuda.first()
        stationObratnoTV.text = tuda.last()
        stopsNumTV.text = (if(isReversed) obratno.size.toString() else tuda.size.toString())+" остановок"


        val routeAdapter = RouteAdapter(tuda,obratno)
        routeResView.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        routeResView.adapter = routeAdapter
        routeAdapter.notifyDataSetChanged()

        reverseIV.setOnClickListener {
            routeAdapter.reverse()
            isReversed = !isReversed
            if(isReversed){
                stationTudaTV.text = obratno.first()
                stationTudaTV.setTextColor(resources.getColor(R.color.red))
                stationObratnoTV.text = obratno.last()
                stationObratnoTV.setTextColor(resources.getColor(R.color.green))
            }else{
                stationTudaTV.text = tuda.first()
                stationTudaTV.setTextColor(resources.getColor(R.color.green))
                stationObratnoTV.text = tuda.last()
                stationObratnoTV.setTextColor(resources.getColor(R.color.red))
            }
            stopsNumTV.text = (if(isReversed) obratno.size.toString() else tuda.size.toString())+" остановок"
        }
    }
}