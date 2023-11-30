package com.example.turfbooking

import adapters.MyAdapter4ViewBooking
import android.annotation.SuppressLint
import android.os.Bundle
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_booking)

        newRecyclerView = findViewById(R.id.recycleview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        myAdapter = MyAdapter4ViewBooking(newArrayList)
        newRecyclerView.adapter = myAdapter

        // Initialize Firebase Database reference for "customer_info" node
        val customerInfoRef = FirebaseDatabase.getInstance().reference.child("customer_info")

        // Fetch data from "customer_info" node
        fetchDataFromFirebase(customerInfoRef)
    }

    private fun fetchDataFromFirebase(databaseRef: DatabaseReference) {
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    newArrayList.clear() // Clear previous data if any

                    for (dataSnapshot in snapshot.children) {
                        val customerName = dataSnapshot.child("Fullname").getValue(String::class.java) ?: ""
                        val mobileNumber = dataSnapshot.child("Mobilenumber").getValue(String::class.java) ?: ""
                        val gameName =
                            dataSnapshot.child("GameName").getValue(String::class.java) ?: ""
                        val teamSize =
                            dataSnapshot.child("TeamSize").getValue(String::class.java) ?: ""


                        val new = New(customerName, mobileNumber, gameName, teamSize)
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