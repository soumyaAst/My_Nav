package com.example.SMSManager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SMSManager.Database
import com.example.SMSManager.R
import kotlinx.android.synthetic.main.cat_recyclerview.view.*

class CategoryAdapter(var category:String,var size:Int): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.cat_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_catrv.text=category
            remove_cat.setOnClickListener {
                val dbHelper =
                    Database(holder.itemView.context)
                val db = dbHelper.writableDatabase
                var new= arrayOf<String>(category)
                db.delete("CATEGORIES_TABLE","CATEGORY = ? ",new)
                notifyItemRemoved(position)
                notifyDataSetChanged()
            }
        }
    }
}