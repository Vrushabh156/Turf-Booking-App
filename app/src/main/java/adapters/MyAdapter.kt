package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turfbooking.DetailActivity
import com.example.turfbooking.R
import models.DataClass

class MyAdapter(private val context: Context, private var dataList: List<DataClass>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].dataImage).into(holder.recImage)
        holder.recTitle.text = dataList[position].dataTitle
        holder.recDesc.text = dataList[position].dataDesc
        holder.recLang.text = dataList[position].dataLang

        holder.bookButton.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("Image", dataList[position].dataImage)
                putExtra("Description", dataList[position].dataDesc)
                putExtra("Title", dataList[position].dataTitle)
                putExtra("Key", dataList[position].key)
                putExtra("Language", dataList[position].dataLang)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setSearchDataList(searchList: List<DataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recImage: ImageView = itemView.findViewById(R.id.recImage)
        var recDesc: TextView = itemView.findViewById(R.id.recDesc)
        var recLang: TextView = itemView.findViewById(R.id.recPriority)
        var recTitle: TextView = itemView.findViewById(R.id.recTitle)
        var bookButton: Button = itemView.findViewById(R.id.book)
    }
}
