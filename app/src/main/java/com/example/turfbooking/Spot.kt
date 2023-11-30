package com.example.turfbooking

import adapters.MyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import models.DataClass

class Spot : Fragment() {

    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    private var recyclerView: RecyclerView? = null
    private var dataList: MutableList<DataClass> = ArrayList()
    private var adapter: MyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_spot, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView?.layoutManager = gridLayoutManager

        adapter = MyAdapter(requireContext(), dataList)
        recyclerView?.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    dataClass?.key = itemSnapshot.key
                    dataClass?.let { dataList.add(it) }
                }
                adapter?.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        }

        databaseReference?.addValueEventListener(eventListener as ValueEventListener)

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove the ValueEventListener when the fragment is destroyed
        databaseReference?.removeEventListener(eventListener as ValueEventListener)
    }
}
