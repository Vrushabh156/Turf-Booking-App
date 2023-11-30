package com.example.turfbooking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     // Hide ActionBar
        supportActionBar?.hide()
        val btn1 = findViewById<Button>(R.id.btn1)

        btn1.setOnClickListener {
            val intent = Intent(this, OwnerRegistration::class.java)
            startActivity(intent)
        }

        val userRegisterBtn = findViewById<Button>(R.id.userRegisterBtn)
        userRegisterBtn.setOnClickListener {
            val intent = Intent(this, UserRegistration::class.java)
            startActivity(intent)
        }
    }
}