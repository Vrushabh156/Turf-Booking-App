package com.example.turfbooking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class UserRegistration : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        supportActionBar?.hide()

        val btn1 = findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener {
            val intent = Intent(this, UserSignIn::class.java)
            startActivity(intent)
        }

        val registrationBtn = findViewById<Button>(R.id.registrationBtn)
        registrationBtn.setOnClickListener {
            val intent = Intent(this, UserRegister::class.java)
            startActivity(intent)
        }
    }
}