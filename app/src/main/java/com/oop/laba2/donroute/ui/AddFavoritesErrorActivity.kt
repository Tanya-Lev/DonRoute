package com.oop.laba2.donroute.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.oop.laba2.donroute.R

class AddFavoritesErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_favorites_error)

        val regBTN = findViewById<Button>(R.id.regBTN)
        val loginBTN = findViewById<Button>(R.id.loginBTN)
        findViewById<ImageView>(R.id.backIV).setOnClickListener {
            finish()
        }

        regBTN.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
        loginBTN.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}