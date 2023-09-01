package com.example.turfbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UserSignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_in)
        supportActionBar?.hide()
    }
}