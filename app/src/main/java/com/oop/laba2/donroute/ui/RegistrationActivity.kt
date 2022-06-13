package com.oop.laba2.donroute.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oop.laba2.donroute.network.NetWorkAPI
import com.oop.laba2.donroute.R
import com.oop.laba2.donroute.Utils

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val email = findViewById<EditText>(R.id.regEmailET)
        val firstPass = findViewById<EditText>(R.id.regFirstPassET)
        val secPass = findViewById<EditText>(R.id.regSecPassET)
        val send = findViewById<Button>(R.id.sendRegBtn)
        val eyeIV = findViewById<ImageView>(R.id.eyeIV)

        findViewById<ImageView>(R.id.backIV).setOnClickListener {
            finish()
        }

        var isEyeClosed = false
        eyeIV.setOnClickListener {
            if(isEyeClosed){
                eyeIV.setImageDrawable(getDrawable(R.drawable.ic_eye_closed))
                firstPass.transformationMethod = PasswordTransformationMethod.getInstance()
                firstPass.setSelection(firstPass.length())
            }
            else {
                eyeIV.setImageDrawable(getDrawable(R.drawable.ic_eye))
                firstPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                firstPass.setSelection(firstPass.length())
            }

            isEyeClosed = !isEyeClosed
        }

        send.setOnClickListener {
            if(email.text.isNullOrEmpty() || firstPass.text.isNullOrEmpty() || secPass.text.isNullOrEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(firstPass.text.toString() != secPass.text.toString()) {
                Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!Utils.EmailValidationService.isValidEmail(email.text.toString())){
                Toast.makeText(this, "Неправильно введена Электронная почта! Попробуйте снова.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            it.isEnabled = false
            auth = Firebase.auth

            auth.createUserWithEmailAndPassword(email.text.toString(), firstPass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Аккаунт успешно создан!",
                            Toast.LENGTH_SHORT).show()

                        NetWorkAPI.createFavoritePath(user!!.uid)

                        startActivity(Intent(this,LoginActivity::class.java))
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Ошибка!",
                            Toast.LENGTH_SHORT).show()
                        it.isEnabled = true
                        //updateUI(null)
                    }
                }
        }
    }
}