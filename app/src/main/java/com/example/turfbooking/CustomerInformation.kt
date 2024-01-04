package com.example.turfbooking

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class CustomerInformation : AppCompatActivity() {

    private lateinit var customerNameTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var mobileNumberTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var teamSizeTextView: TextView
    private lateinit var gameNameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView
    private lateinit var conformButton: Button
    private lateinit var cancelButton: Button
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_information)

        customerNameTextView = findViewById(R.id.customerNameTextView)
        addressTextView = findViewById(R.id.addressTextView)
        mobileNumberTextView = findViewById(R.id.mobileNumberTextView)
        emailTextView = findViewById(R.id.emailTextView)
        teamSizeTextView = findViewById(R.id.teamSizeTextView)
        gameNameTextView = findViewById(R.id.gameNameTextView)
        dateTextView = findViewById(R.id.dateTextView)
        startTimeTextView = findViewById(R.id.startTimeTextView)
        endTimeTextView = findViewById(R.id.endTimeTextView)

        conformButton = findViewById(R.id.Conform_btn)
        cancelButton = findViewById(R.id.Cancel_btn)

        databaseReference = FirebaseDatabase.getInstance().reference.child("bookings")

        // Retrieve data from Firebase Realtime Database
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val customerName = dataSnapshot.child("fullName").getValue(String::class.java)
                    val address = dataSnapshot.child("address").getValue(String::class.java)
                    val mobileNumber = dataSnapshot.child("mobileNumber").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)
                    val teamSize = dataSnapshot.child("teamSize").getValue(String::class.java)
                    val gameName = dataSnapshot.child("gameName").getValue(String::class.java)
                    val date = dataSnapshot.child("date").getValue(String::class.java)
                    val startTime = dataSnapshot.child("startTime").getValue(String::class.java)
                    val endTime = dataSnapshot.child("endTime").getValue(String::class.java)

                    // Set retrieved data to TextViews
                    customerNameTextView.text = "Customer Name: $customerName"
                    addressTextView.text = "Address: $address"
                    mobileNumberTextView.text = "Mobile Number: $mobileNumber"
                    emailTextView.text = "Email: $email"
                    teamSizeTextView.text = "Team Size: $teamSize"
                    gameNameTextView.text = "Game Name: $gameName"
                    dateTextView.text = "Date: $date"
                    startTimeTextView.text = "Start Time: $startTime"
                    endTimeTextView.text = "End Time: $endTime"

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur while fetching data
            }
        })

        // Button Click Listeners
        conformButton.setOnClickListener {
            // Navigate to SuccessfullyConfirmedActivity upon "Conform" button click
            val intent = Intent(this@CustomerInformation, SuccessfullyConfirmed::class.java)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val specificActivityReference = FirebaseDatabase.getInstance().reference
                .child("bookings") // Change this to your specific node

            specificActivityReference.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@CustomerInformation, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // Handle delete operation failure
                    Toast.makeText(
                        this@CustomerInformation,
                        "Information couldn't be deleted. Contact Vaibhav for assistance.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
