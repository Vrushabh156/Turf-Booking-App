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

    interface OnItemClickListener {
        fun onItemClick(item: New)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var customerNameTextView: TextView = itemView.findViewById(R.id.customerNameTextView)
        var dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        var startTimeTextView: TextView = itemView.findViewById(R.id.startTimeTextView)
        var endTimeTextView: TextView = itemView.findViewById(R.id.endTimeTextView)
        // Initialize other views here
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener?.onItemClick(newList[position])
            }
        }
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
        holder.customerNameTextView.text = "Customer Name: ${currentItem.customerNameTextView}"
        holder.dateTextView.text = "Date: ${currentItem.dateTextView}"
        holder.startTimeTextView.text = "Start Time: ${currentItem.startTimeTextView}"
        holder.endTimeTextView.text = "End Time: ${currentItem.endTimeTextView}"
        // Bind other data to views
    }
}
