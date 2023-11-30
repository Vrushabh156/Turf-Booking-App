package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turfbooking.New
import com.example.turfbooking.R

class MyAdapter4ViewBooking(private val newList: ArrayList<New>) :
    RecyclerView.Adapter<MyAdapter4ViewBooking.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleTextView: TextView = itemView.findViewById(R.id.dateTextView)
        var titleTextView3: TextView = itemView.findViewById(R.id.startTimeTextView)
        var titleTextView4: TextView = itemView.findViewById(R.id.endTimeTextView)
        var titleTextView5: TextView = itemView.findViewById(R.id.customerNameTextView)
        // Initialize other views here
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.titleTextView.text = "Customer Name: ${currentItem.textView}"
        holder.titleTextView3.text = "Mobile Number: ${currentItem.textView3}"
        holder.titleTextView4.text = "Game Name: ${currentItem.textView4}"
        holder.titleTextView5.text = "Team Size: ${currentItem.titleTextView5}"
        // Bind other data to views
    }
}
