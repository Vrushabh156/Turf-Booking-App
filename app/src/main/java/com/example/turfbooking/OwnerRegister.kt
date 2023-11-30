package com.example.turfbooking

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.turfbooking.databinding.ActivityOwnerRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class OwnerRegister : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerRegisterBinding
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
        binding = ActivityOwnerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val firstNameText = binding.firstName.text.toString().trim()
            val lastNameText = binding.lastName.text.toString().trim()
            val emailText = binding.email.text.toString().trim()
            val passwordText = binding.password.text.toString().trim()
            val dobText = binding.dob.text.toString().trim()
            val mobileNoText = binding.mobileNo.text.toString().trim()
            val addressText = binding.address.text.toString().trim()


            if (TextUtils.isEmpty(firstNameText)) {
                Toast.makeText(this, "Enter first name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(lastNameText)) {
                Toast.makeText(this, "Enter last name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(emailText)) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(passwordText)) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(dobText)) {
                Toast.makeText(this, "Enter date of birth", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(mobileNoText)) {
                Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(addressText)) {
                Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account created.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, OwnerDashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}