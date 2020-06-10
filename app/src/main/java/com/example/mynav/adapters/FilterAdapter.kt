package com.example.mynav.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynav.Database
import com.example.mynav.R
import com.example.mynav.util.AddFilter
import kotlinx.android.synthetic.main.add_filter.view.*
import kotlinx.android.synthetic.main.filter_recyclerview.view.*
import kotlinx.android.synthetic.main.individual_card.view.*
import kotlinx.android.synthetic.main.recyclerviewobj.view.*

class FilterAdapter (var category:String,var list:MutableList<String>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.filter_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_indfilter.text=list[position]
            remove_filter.setOnClickListener {
                val dbHelper = Database(holder.itemView.context)
                val db = dbHelper.writableDatabase
                var new= arrayOf<String>(category,list[position])
                db.delete("CATEGORIES_TABLE","CATEGORY = ? AND FILTER = ?",new)
                list.removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }
}