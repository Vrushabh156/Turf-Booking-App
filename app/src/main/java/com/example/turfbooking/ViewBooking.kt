package com.example.turfbooking

import adapters.MyAdapter4ViewBooking
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ViewBooking : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<New>
    private lateinit var databaseRef: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener
    private lateinit var myAdapter: MyAdapter4ViewBooking

//    private lateinit var more: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_booking)
//
//        more = findViewById(R.id.moreTextView)
//
//        more.setOnClickListener {
//            // Navigate to SuccessfullyConfirmedActivity upon "Conform" button click
//            val intent = Intent(this@ViewBooking, CustomerInformation::class.java)
//            startActivity(intent)
//        }

        newRecyclerView = findViewById(R.id.recycleview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        myAdapter = MyAdapter4ViewBooking(newArrayList)
        newRecyclerView.adapter = myAdapter

        // Initialize Firebase Database reference for "customer_info" node
        val customerInfoRef = FirebaseDatabase.getInstance().reference.child("bookings")

        // Fetch data from "customer_info" node
        fetchDataFromFirebase(customerInfoRef)
        myAdapter.setOnItemClickListener(object : MyAdapter4ViewBooking.OnItemClickListener {
            override fun onItemClick(item: New) {
                val intent = Intent(this@ViewBooking,CustomerInformation::class.java)
                // Pass data to the next activity if needed: intent.putExtra("key", value)
                startActivity(intent)
            }
        })
    }

    private fun fetchDataFromFirebase(databaseRef: DatabaseReference) {
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    newArrayList.clear() // Clear previous data if any

                    for (dataSnapshot in snapshot.children) {
                        val customerName = dataSnapshot.child("fullName").getValue(String::class.java) ?: ""
                        val date = dataSnapshot.child("date").getValue(String::class.java) ?: ""
                        val startTime = dataSnapshot.child("startTime").getValue(String::class.java) ?: ""
                        val endTime = dataSnapshot.child("endTime").getValue(String::class.java) ?: ""

                        val new = New(customerName, date, startTime, endTime)
                        newArrayList.add(new)
                    }
                    myAdapter.notifyDataSetChanged() // Notify adapter of changes
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        }

        databaseRef.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the ValueEventListener when the activity is destroyed to prevent memory leaks
        databaseRef.removeEventListener(valueEventListener)
    }
}