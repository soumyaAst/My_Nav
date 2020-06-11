package com.example.SMSManager.util

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SMSManager.R
import com.example.SMSManager.adapters.IndividualAdapter
import com.example.SMSManager.adapters.MsgAdapter
import kotlinx.android.synthetic.main.activity_message_screen2.*
import kotlinx.android.synthetic.main.unknown_frag.*

class MessageScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var name = intent.getStringExtra("NAME_EXTRA")
        var message=intent.getStringExtra("LAST_MESSAGE")
        var num=intent.getStringExtra("NUMBER_EXTRA")
        if(num != "SMSBOX") {
            setContentView(R.layout.activity_message_screen2)
            //Getting The Messages for Corresponding Number//
            var listofnums = MainActivity.msglist[num]

            if (listofnums == null) {
                var newlist = mutableListOf<String>(message)
                MainActivity.msglist[num] = newlist
                var adapter =
                    IndividualAdapter(newlist)
                //Toast.makeText(this, MainActivity.msglist[name]!![1],Toast.LENGTH_SHORT).show()
                rv_individual.adapter = adapter
                rv_individual.layoutManager = LinearLayoutManager(this)
            } else {

                var adapter =
                    IndividualAdapter(
                        listofnums
                    )
                //Toast.makeText(this, MainActivity.msglist[name]!![1],Toast.LENGTH_SHORT).show()
                rv_individual.adapter = adapter
                rv_individual.layoutManager = LinearLayoutManager(this)
            }
        }
        else{
            setContentView(R.layout.unknown_frag)
            //Toast.makeText(this,"IN ELSE",Toast.LENGTH_SHORT).show()
//            val dbHelper = Database(this)
//            val db = dbHelper.writableDatabase
//            var categorylist= arrayListOf<String>()
//            categorylist=AddFilter.getFilterList(name,db)
//
//            var newlist= mutableListOf<MsgObj>()
//            Log.d("database","Starting")
//            Log.d("database",categorylist.toString())
//            for (msg in MainActivity.unknownlist){
//                if(msg.name in categorylist){
//                    newlist.add(msg)
//                }
//                for(each in categorylist){
//                    if(msg.message.contains(each,true)){
//                        newlist.add(msg)
//                    }
//                }
//
//            }
//            //Log.d("database","Message 0"+newlist[0].message)
            var newadapter= MainActivity.boxlist[name]?.let {
                MsgAdapter(
                    it
                )
            }
            rv_unknownfrag.adapter=newadapter
            rv_unknownfrag.layoutManager=LinearLayoutManager(this)

        }
    }
}
