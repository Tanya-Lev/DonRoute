package com.oop.laba2.donroute.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oop.laba2.donroute.AllTransport
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.adapters.TransportNumbersAdapter
import com.oop.laba2.donroute.enteties.Transport
import com.oop.laba2.donroute.enteties.TransportType

class TransportNumbersActivity : AppCompatActivity() {

    var layoutState = 0


    lateinit var transportList : List<Transport>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_numbers)

        if(intent.hasExtra("layoutState"))
            layoutState = intent.getIntExtra("layoutState",0)

        //0 автобус 1 тролебус 2 трамвай 3 избранное 4 автостанция

        findViewById<ImageView>(R.id.backIV).setOnClickListener {
            finish()
        }

        val busTV = findViewById<TextView>(R.id.busTV)
        val busRV = findViewById<RecyclerView>(R.id.busRV)
        val trolleybusTV = findViewById<TextView>(R.id.trolleybusTV)
        val trolleybusRV = findViewById<RecyclerView>(R.id.trolleybusRV)
        val tramTV = findViewById<TextView>(R.id.tramTV)
        val tramRV = findViewById<RecyclerView>(R.id.tramRV)
        val stationTV = findViewById<TextView>(R.id.stationTV)
        val favouritesFAB = findViewById<FloatingActionButton>(R.id.favouritesFAB)

        busRV.layoutManager = object:GridLayoutManager(this, 5){ override fun canScrollVertically(): Boolean { return false } }
        trolleybusRV.layoutManager = object:GridLayoutManager(this, 5){ override fun canScrollVertically(): Boolean { return false } }
        tramRV.layoutManager = object:GridLayoutManager(this, 5){ override fun canScrollVertically(): Boolean { return false } }

        transportList = AllTransport.allTransport

        favouritesFAB.setOnClickListener {
            val intent = Intent(this,TransportNumbersActivity::class.java)
            intent.putExtra("layoutState",3)
            startActivity(intent)
        }

        when(layoutState){
            0,1,2 -> {
                //busRV.setPadding(0,0,0,-123.px)
                setInvisible(busTV)
                setInvisible(trolleybusTV)
                setInvisible(trolleybusRV)
                setInvisible(tramTV)
                setInvisible(tramRV)
                //setInvisible(favouritesFAB)
            }
            3->{
                stationTV.text = "Избранное"
                transportList = AllTransport.favoriteTransport

                setInvisible(favouritesFAB)
            }
            4-> {
                val station = if (intent.hasExtra("station")) intent.getStringExtra("station") else "Станция"
                stationTV.text = station
                transportList = AllTransport.allTransport.filter {
                    it.tuda!!.contains(station) || it.obratno!!.contains(station)
                }
            }
        }

        when(layoutState){
            0 -> {
                stationTV.text = "Автобусы"
                busRV.adapter = TransportNumbersAdapter(transportList.filter { it.transportType == TransportType.BUS })

            }
            1 -> {
                stationTV.text = "Троллейбусы"
                busRV.adapter = TransportNumbersAdapter(transportList.filter { it.transportType == TransportType.TROLLEYBUS })
            }
            2 -> {
                stationTV.text = "Трамваи"
                busRV.adapter = TransportNumbersAdapter(transportList.filter { it.transportType == TransportType.TRAM })
            }
            else ->{
                busRV.adapter = TransportNumbersAdapter(transportList.filter { it.transportType == TransportType.BUS })
                trolleybusRV.adapter = TransportNumbersAdapter(transportList.filter { it.transportType == TransportType.TROLLEYBUS })
                tramRV.adapter = TransportNumbersAdapter(transportList.filter { it.transportType == TransportType.TRAM })

                if(trolleybusRV.adapter?.itemCount == 0){
                    setInvisible(trolleybusTV)
                    setInvisible(trolleybusRV)
                }
                if(tramRV.adapter?.itemCount == 0){
                    setInvisible(tramTV)
                    setInvisible(tramRV)
                }
                if(busRV.adapter?.itemCount == 0){
                    setInvisible(busTV)
                    setInvisible(busRV)
                }

                trolleybusRV.adapter!!.notifyDataSetChanged()
                tramRV.adapter!!.notifyDataSetChanged()
            }
        }

        busRV.adapter!!.notifyDataSetChanged()

    }
    fun setInvisible(v: View){
        v.visibility = View.GONE
    }
}