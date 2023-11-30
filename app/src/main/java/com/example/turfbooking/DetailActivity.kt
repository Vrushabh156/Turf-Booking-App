package com.example.turfbooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var detailDesc: TextView
    private lateinit var detailTitle: TextView
    private lateinit var detailImage: ImageView
    private lateinit var paybtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        paybtn = findViewById(R.id.book_now)

        paybtn.setOnClickListener {
//            makePayment()
            val intent = Intent(this, UserDetails::class.java)
            startActivity(intent)
        }

        // Initialize your views using findViewById
        detailDesc = findViewById(R.id.detailDesc)
        detailImage = findViewById(R.id.detailImage)
        detailTitle = findViewById(R.id.detailTitle)

        // Retrieve data from the intent
        val bundle = intent.extras
        bundle?.let {
            val description = it.getString("Description")
            val title = it.getString("Title")
            val imageUrl = it.getString("Image")

            // Check if the values are not null before setting them to TextViews
            detailDesc.text = description ?: ""
            detailTitle.text = title ?: ""

            // Load the image using Glide if the URL is not null
            imageUrl?.let {
                Glide.with(this)
                    .load(it)
                    .into(detailImage)
            }
        }
    }
}
