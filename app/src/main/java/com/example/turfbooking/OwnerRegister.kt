package com.example.turfbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OwnerRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_register)
        supportActionBar?.hide()
    }
}