package com.oop.laba2.donroute.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.*
import android.text.method.HideReturnsTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oop.laba2.donroute.R
import android.text.method.PasswordTransformationMethod
import com.oop.laba2.donroute.Utils


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val email = findViewById<EditText>(R.id.loginEmailET)
        val pass = findViewById<EditText>(R.id.loginPassET)
        val logInBtn = findViewById<Button>(R.id.logInBtn)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val eyeIV = findViewById<ImageView>(R.id.eyeIV)

        registerBtn.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
        findViewById<ImageView>(R.id.backIV).setOnClickListener {
            finish()
        }

        var isEyeClosed = false
        eyeIV.setOnClickListener {
            if(isEyeClosed){
                eyeIV.setImageDrawable(getDrawable(R.drawable.ic_eye_closed))
                pass.transformationMethod = PasswordTransformationMethod.getInstance()
                pass.setSelection(pass.length())
            }
            else {
                eyeIV.setImageDrawable(getDrawable(R.drawable.ic_eye))
                pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                pass.setSelection(pass.length())
            }

            isEyeClosed = !isEyeClosed
        }


        logInBtn.setOnClickListener {

            if(email.text.isNullOrEmpty() || pass.text.isNullOrEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!Utils.EmailValidationService.isValidEmail(email.text.toString())){
                Toast.makeText(this, "Неправильно введена Электронная почта! Попробуйте снова.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth = Firebase.auth

            auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Вы вошли!",
                            Toast.LENGTH_SHORT).show()

                        startActivity(
                            Intent(
                                this,
                                ChooseTransportTypeActivity::class.java).also {
                                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                        )
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Ошибка входа! Проверьте введённые данные.",
                            Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }
}