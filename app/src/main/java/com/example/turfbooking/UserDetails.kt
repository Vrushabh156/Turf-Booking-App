package com.example.turfbooking

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class UserDetails : AppCompatActivity(), PaymentResultListener {

    private lateinit var datePicker: DatePicker
    private lateinit var startTimeSpinner: Spinner
    private lateinit var endTimeSpinner: Spinner
    private lateinit var paytext: TextView
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_details)

        databaseReference = FirebaseDatabase.getInstance().reference.child("bookings")

        val payNow: Button = findViewById(R.id.Pay)
        paytext = findViewById(R.id.tvMsg)

        setupDatePicker()
        setupTimeSpinners()

        payNow.setOnClickListener {
            handlePaymentButtonClick()
        }
    }

    private fun setupDatePicker() {
        datePicker = findViewById(R.id.datePicker)
        // Set up date picker
        datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.maxDate = Long.MAX_VALUE
    }

    private fun setupTimeSpinners() {
        startTimeSpinner = findViewById(R.id.startTimeSpinner)
        endTimeSpinner = findViewById(R.id.endTimeSpinner)
        // Set up slots for the time Spinners
        val slots = arrayOf(
            "Time", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM",
            "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, slots)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        startTimeSpinner.adapter = adapter
        endTimeSpinner.adapter = adapter
    }

    private fun handlePaymentButtonClick() {
        val selectedDate = getDateFromDatePicker(datePicker)
        val selectedStartTime = startTimeSpinner.selectedItem as String
        val selectedEndTime = endTimeSpinner.selectedItem as String

        val fullNameEditText: EditText = findViewById(R.id.NamePerson)
        val addressEditText: EditText = findViewById(R.id.adressPerson)
        val mobileNumberEditText: EditText = findViewById(R.id.mobileNo)
        val emailEditText: EditText = findViewById(R.id.emailPerson)
        val teamSizeEditText: EditText = findViewById(R.id.teamSize)
        val gameNameEditText: EditText = findViewById(R.id.gameName)

        val fullName = fullNameEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        val mobileNumber = mobileNumberEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val teamSize = teamSizeEditText.text.toString().trim()
        val gameName = gameNameEditText.text.toString().trim()

        // Validate fields
        if (isNotEmpty(fullName, address, mobileNumber, email, teamSize, gameName, selectedDate, selectedStartTime, selectedEndTime)) {
            // Check for time slot availability
            checkTimeSlotAvailability(
                fullName, address, mobileNumber, email, teamSize, gameName,
                selectedDate, selectedStartTime, selectedEndTime
            )
        } else {
            Toast.makeText(this@UserDetails, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNotEmpty(vararg values: String): Boolean {
        return values.all { it.isNotEmpty() }
    }

    private fun checkTimeSlotAvailability(
        fullName: String, address: String, mobileNumber: String, email: String,
        teamSize: String, gameName: String, selectedDate: String, selectedStartTime: String,
        selectedEndTime: String
    ) {
        val bookingRef = databaseReference.orderByChild("startTime").equalTo(selectedStartTime)

        bookingRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var isTimeSlotAvailable = true

                for (bookingSnapshot in dataSnapshot.children) {
                    val endTime = bookingSnapshot.child("endTime").getValue(String::class.java)
                    if (endTime == selectedEndTime) {
                        // If a booking with the same start and end time exists
                        isTimeSlotAvailable = false
                        break
                    }
                }

                if (isTimeSlotAvailable) {
                    // Save booking data and proceed with payment
                    saveBookingData(
                        fullName, address, mobileNumber, email, teamSize, gameName,
                        selectedDate, selectedStartTime, selectedEndTime
                    )
                } else {
                    // Display a message that the time slot is not available
                    Toast.makeText(this@UserDetails, "Time slot not available", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Toast.makeText(this@UserDetails, "Database error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveBookingData(
        fullName: String, address: String, mobileNumber: String, email: String,
        teamSize: String, gameName: String, selectedDate: String, selectedStartTime: String,
        selectedEndTime: String
    ) {
        val bookingMap: HashMap<String, Any> = HashMap()
        bookingMap["fullName"] = fullName
        bookingMap["address"] = address
        bookingMap["mobileNumber"] = mobileNumber
        bookingMap["email"] = email
        bookingMap["teamSize"] = teamSize
        bookingMap["gameName"] = gameName
        bookingMap["date"] = selectedDate
        bookingMap["startTime"] = selectedStartTime
        bookingMap["endTime"] = selectedEndTime

        databaseReference.push().setValue(bookingMap)
            .addOnSuccessListener {
                // Data successfully saved in Firebase
//                Toast.makeText(this@UserDetails, "Data added successfully", Toast.LENGTH_SHORT).show()
                makePayment()
            }
            .addOnFailureListener {
                // Handle the error if data saving fails
                Toast.makeText(this@UserDetails, "Failed to add data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun makePayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_hmxV0SdHq9XRU1") // Replace with your actual Razorpay key

        checkout.setImage(R.drawable.img)
        val activity: Activity = this

        try {
            val options = JSONObject()

            options.put("name", "ALGORIAL EDUCARE")
            options.put("description", "Reference No. #123456")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", "100000") // Replace with the actual amount
            options.put("prefill.email", "gaurav.kumar@example.com")
            options.put("prefill.contact", "9172806302")
            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onPaymentSuccess(s: String?) {
        paytext.text = "Successful payment ID: $s"
    }

    override fun onPaymentError(i: Int, s: String?) {
        paytext.text = "Failed and cause is: $s"
    }
}
