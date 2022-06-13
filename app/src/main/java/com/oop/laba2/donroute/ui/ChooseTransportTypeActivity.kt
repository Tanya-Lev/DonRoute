package com.oop.laba2.donroute.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oop.laba2.donroute.AllTransport
import com.oop.laba2.donroute.AllTransport.allStops
import com.oop.laba2.donroute.network.NetWorkAPI
import com.oop.laba2.donroute.R

class ChooseTransportTypeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_transport_type)

        auth = Firebase.auth
        val busCV = findViewById<CardView>(R.id.busCV)
        val trolleybusCV = findViewById<CardView>(R.id.trolleybusCV)
        val tramCV = findViewById<CardView>(R.id.tramCV)
        val favouritesFAB = findViewById<FloatingActionButton>(R.id.favouritesFAB)
        val intent = Intent(this,TransportNumbersActivity::class.java)
        val openLogin = findViewById<TextView>(R.id.openLogin)
        val chooseStationBTN = findViewById<Button>(R.id.chooseStationBTN)
        val searchStopView = findViewById<SearchView>(R.id.searchStopView)
        val searchListView = findViewById<ListView>(R.id.searchListView)

//-------------search logic----------------

        var transportAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,
            allStops
        )

        searchListView.setOnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(this@ChooseTransportTypeActivity,TransportNumbersActivity::class.java)
            intent.putExtra("station",(view as TextView).text)
            intent.putExtra("layoutState",4)
            startActivity(intent)
        }

        searchStopView.setOnQueryTextFocusChangeListener { view, b ->
            if(b) {
                transportAdapter = ArrayAdapter<String>(
                    this,android.R.layout.simple_list_item_1,
                    allStops
                )
                searchListView.adapter = transportAdapter
                searchListView.visibility = View.VISIBLE
            }else
                searchListView.visibility = View.INVISIBLE
        }
        searchStopView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchStopView.clearFocus()
                if (allStops.any {it.contains(query!!,true)}){
                    transportAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    transportAdapter.filter.filter(newText)

                return false
            }


        })

//-------------search logic----------------




        chooseStationBTN.setOnClickListener {
            startActivity(Intent(this,ChooseStationActivity::class.java))
        }

        if(auth.uid!=null) {
            NetWorkAPI.getAllFavorite(Firebase.auth.uid!!)
            openLogin.text = "Выйти"
        }

        openLogin.setOnClickListener {
            if(auth.uid==null)
                startActivity(Intent(this,LoginActivity::class.java))
            else{
                auth.signOut()
                openLogin.text = "Войти"
                Toast.makeText(this, "Вы вышли из своего аккаунта.", Toast.LENGTH_SHORT).show()
                AllTransport.allTransport.forEach {
                    if(it._isFavorite?:false)
                        it._isFavorite = false
                }
            }
        }
        busCV.setOnClickListener {
            intent.putExtra("layoutState",0)
            startActivity(intent)
        }
        trolleybusCV.setOnClickListener {
            intent.putExtra("layoutState",1)
            startActivity(intent)
        }
        tramCV.setOnClickListener {
            intent.putExtra("layoutState",2)
            startActivity(intent)
        }
        favouritesFAB.setOnClickListener {
            if(auth.uid!=null){
                intent.putExtra("layoutState", 3)
                startActivity(intent)
            }else{
                startActivity(Intent(this,AddFavoritesErrorActivity::class.java))
            }
        }
    }
}