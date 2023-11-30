package com.example.turfbooking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.denzcoskun.imageslider.ImageSlider
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.turfbooking.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        firebaseAuth = FirebaseAuth.getInstance()

        user = firebaseAuth.currentUser ?: throw IllegalStateException("User is null")
        binding.username.text = user.email

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://assets.telegraphindia.com/telegraph/2021/Sep/1630669298_sky-turf.jpg","Astro turfs in Kolkata", ScaleTypes.FIT))
        imageList.add(SlideModel("https://images.livemint.com/rf/Image-621x414/LiveMint/Period1/2015/09/12/Photos/turf-kHJF--621x414@LiveMint.jpg","The rooftop turf at The Arena",ScaleTypes.FIT))
        imageList.add(SlideModel("https://images.newindianexpress.com/uploads/user/imagelibrary/2020/6/19/w900X450/Football_Truf_EPS.jpg?dpr=1.0&q=70&w=640","Football turfs",ScaleTypes.FIT))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

//        binding.allTurf.setOnClickListener {
////            Navigation.findNavController(view).navigate(R.id.fragment_spot)
//            val intent = Intent(requireContext(), Spot::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//        }
        return view
    }
}
