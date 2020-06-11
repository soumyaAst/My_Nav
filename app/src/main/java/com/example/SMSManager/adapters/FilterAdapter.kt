package com.example.SMSManager.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SMSManager.Database
import com.example.SMSManager.R
import kotlinx.android.synthetic.main.filter_recyclerview.view.*


class FilterAdapter(var category:String, var list: MutableList<String>): RecyclerView.Adapter<ViewHolder>(){
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
                notifyDataSetChanged()
                val dbHelper =
                    Database(holder.itemView.context)
                val db = dbHelper.writableDatabase
                var new= arrayOf<String>(category,list[position])
                Log.d("list","removing $category 's "+list[position])
                db.delete("CATEGORIES_TABLE","CATEGORY = ? AND FILTER = ?",new)
                list.removeAt(position)
                Log.d("list",list.count().toString())
                notifyItemRemoved(position)
            }
        }

    }
}