package com.example.turfbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.turfbooking.databinding.ActivityOwnSignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class OwnSignIn : AppCompatActivity() {
    private lateinit var binding: ActivityOwnSignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, OwnerDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@OwnSignIn, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@OwnSignIn, "Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@OwnSignIn, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@OwnSignIn, "Login Successfully.", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(applicationContext, OwnerDashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@OwnSignIn,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}