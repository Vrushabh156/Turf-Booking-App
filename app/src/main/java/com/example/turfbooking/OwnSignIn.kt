package com.example.turfbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OwnSignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_own_sign_in)
        supportActionBar?.hide()
    }
}