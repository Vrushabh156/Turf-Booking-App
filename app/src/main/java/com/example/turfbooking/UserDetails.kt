package com.example.turfbooking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDetails : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_details)

        databaseReference = FirebaseDatabase.getInstance().reference.child("customer_info")
        val fullNameEditText: EditText = findViewById(R.id.NamePerson)
        val addressEditText: EditText = findViewById(R.id.adressPerson)
        val mobileNumberEditText: EditText = findViewById(R.id.mobileNo)
        val emailEditText: EditText = findViewById(R.id.emailPerson)
        val teamSizeEditText: EditText = findViewById(R.id.teamSize)
        val gameNameEditText: EditText = findViewById(R.id.gameName)
        val nextButton = findViewById<Button>(R.id.NextButton)

        nextButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val mobileNumber = mobileNumberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val teamSize = teamSizeEditText.text.toString().trim()
            val gameName = gameNameEditText.text.toString().trim()

            // Validate and save data to Firebase Database
            if (fullName.isNotEmpty() && address.isNotEmpty() && mobileNumber.isNotEmpty()
                && email.isNotEmpty() && teamSize.isNotEmpty() && gameName.isNotEmpty()
            ) {
                saveUserData(fullName, address, mobileNumber, email, teamSize, gameName)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(
        fullName: String,
        address: String,
        mobileNumber: String,
        email: String,
        teamSize: String,
        gameName: String
    ) {
        // Create a HashMap to store user data
        val userData: HashMap<String, Any> = HashMap()
        userData["Fullname"] = fullName
        userData["Address"] = address
        userData["Mobilenumber"] = mobileNumber
        userData["Email"] = email
        userData["TeamSize"] = teamSize
        userData["GameName"] = gameName

        // Save the user data to Firebase Realtime Database
        databaseReference.push().setValue(userData)
            .addOnSuccessListener {
                // Data successfully saved
                Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TimeSlot::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                // Handle the error if data saving fails
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show()
            }
    }
}
