package com.example.turfbooking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.turfbooking.databinding.FragmentOwnerHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class OwnerHomeFragment : Fragment() {
    private lateinit var binding: FragmentOwnerHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOwnerHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser ?: throw IllegalStateException("User is null")
        binding.username.setText(user.displayName)

        binding.linearLayout.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.fragment_spot)
            val intent = Intent(requireContext(), TurfRegister::class.java)
            startActivity(intent)
//            requireActivity().finish()
        }
        binding.viewBooking.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.fragment_spot)
            val intent = Intent(requireContext(), ViewBooking::class.java)
            startActivity(intent)
//            requireActivity().finish()
        }
        return view
    }
}