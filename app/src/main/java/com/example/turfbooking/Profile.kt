package com.example.turfbooking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.turfbooking.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()

        user = firebaseAuth.currentUser!!
        if (user == null) {
            val intent = Intent(requireContext(), UserRegister::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.logout.setOnClickListener {
            try {
                firebaseAuth.signOut()
                val intent = Intent(requireContext(), UserSignIn::class.java)
                startActivity(intent)
                requireActivity().finish()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Logout failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
