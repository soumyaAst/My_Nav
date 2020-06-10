package com.example.mynav.adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mynav.objects.MsgObj
import com.example.mynav.R
import com.example.mynav.objects.MsgList
import com.example.mynav.util.MainActivity
import com.example.mynav.util.MessageScreen
import kotlinx.android.synthetic.main.recyclerviewobj.view.*

public class MsgAdapter (
    var msglist:List<MsgObj>
): RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewobj,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return msglist.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            contact.text = msglist[position].name
                msg_content.text = msglist[position].message
            contact.contentDescription=msglist[position].num
        }
        holder.itemView.setOnClickListener{
            //Toast.makeText(holder.itemView.context,"CLICKED ON ${msglist[position].name}",Toast.LENGTH_SHORT).show()
            var intent = Intent(holder.itemView.context, MessageScreen::class.java)
            intent.putExtra("NAME_EXTRA","${msglist[position].name}")
            intent.putExtra("LAST_MESSAGE","${msglist[position].message}")
            intent.putExtra("NUMBER_EXTRA","${msglist[position].num}")
            startActivity(holder.itemView.context,intent,null)
            }
        }
    }


interface OnClickListener {
    fun onClick(msgList: MsgList)
}

public class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)