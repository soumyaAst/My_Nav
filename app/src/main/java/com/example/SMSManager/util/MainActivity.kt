package com.example.SMSManager.util

import android.Manifest
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.SMSManager.Database
import com.example.SMSManager.objects.MsgList
import com.example.SMSManager.objects.MsgObj
import com.example.SMSManager.R
import com.example.SMSManager.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

public class MainActivity : AppCompatActivity() {


//variables-------------------------------------------//
    companion object {
    public  var unknownlist=mutableListOf<MsgObj>()
    public  var unknownlist2=mutableListOf<MsgObj>()
    public lateinit var inst: MainActivity
    public lateinit var categorylist:MutableList<String>
        public  lateinit var knownlist:MutableList<MsgObj>
        public lateinit var msglist :HashMap<String,MutableList<String>>
        public lateinit var boxlist:HashMap<String,MutableList<MsgObj>>

        public lateinit var contactlist: HashMap<String,String>
    public var mslist: MsgList?=null
        public fun instance() : MainActivity
    {
            return inst;
        }
    }

    private val list = listOf<String>("PERSONAL","OTHERS")
    private lateinit var localBroadcastManager: LocalBroadcastManager

    private lateinit var adapter: ViewPagerAdapter
    private var reqcode: Int = 0

    private var new = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_CONTACTS
    )
    private lateinit var name:String
    private lateinit var msg:String



//---------------------------------------variables-----------------------//


    private fun initalise(){
        categorylist = mutableListOf<String>()
        knownlist = mutableListOf<MsgObj>()
        msglist =HashMap<String,MutableList<String>>()
        unknownlist = mutableListOf<MsgObj>()
        contactlist = HashMap<String,String>()
        boxlist =HashMap<String,MutableList<MsgObj>>()
        mslist =null

    }


//---------------------------------------overriding functions-----------------//


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getperm()
        initalise()
        val dbHelper = Database(this)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var spinlist= arrayListOf<String>()
        spinlist= AddFilter.getCategories(
            spinlist,
            db
        ) as ArrayList<String>
        for(i in spinlist){
            unknownlist.add(
                MsgObj(i, "", "SMSBOX")
            )
            categorylist.add(i)
        }
        saveContacts()
        refreshSMSbox()
        preparelist()
        prepareboxes(db)
        mslist =
            MsgList(
                knownlist,
                unknownlist2
            )
        adapter= ViewPagerAdapter(
            fm = supportFragmentManager,
            lifecycle = lifecycle
        )


        view_pager.adapter=adapter
        TabLayoutMediator(tabLayout,view_pager) { tab, position ->
        tab.text=list[position]
        }.attach()
        floatingActionButton2.visibility= View.INVISIBLE
        floatingActionButton3.visibility=View.INVISIBLE
//        textView.visibility=View.INVISIBLE
//        textView2.visibility=View.INVISIBLE
        var isfb1click=false
//        tv_123.text="Hi heello"
//        tv_123.visibility= VISIBLE
//        textView42.text="Look at This"
//        textView42.visibility= VISIBLE
        floatingActionButton1.setOnClickListener {
            if(!isfb1click) {
//                textView42.text="HiBruh"
//                textView42.visibility=View.VISIBLE
                floatingActionButton2.visibility = VISIBLE
                floatingActionButton3.visibility = VISIBLE

//                textView.visibility=View.VISIBLE
//                textView2.visibility=View.VISIBLE
                isfb1click=true
            }
            else{
                floatingActionButton2.visibility= View.INVISIBLE
                floatingActionButton3.visibility=View.INVISIBLE
//                textView.visibility=View.INVISIBLE
//                textView2.visibility=View.INVISIBLE
                isfb1click=false
            }

        }

        floatingActionButton2.setOnClickListener{
            floatingActionButton2.visibility= View.INVISIBLE
            floatingActionButton3.visibility=View.INVISIBLE
            var intent= Intent(this, AddCategory::class.java)
            startActivity(intent)
            isfb1click=false
        }
        floatingActionButton3.setOnClickListener{
            floatingActionButton2.visibility= View.INVISIBLE
            floatingActionButton3.visibility=View.INVISIBLE
            var intent= Intent(this, AddFilter::class.java)
            startActivity(intent)
            isfb1click=false
        }
    db.close()
        dbHelper.close()
        }

    override fun onPause() {

        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        inst =this
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }
//-----------------------------------overriding functions -----------------------//


    private fun getperm() {
        makerequest()
    }

    private fun makerequest() {
        ActivityCompat.requestPermissions(this, new, reqcode)
    }


    private fun saveContacts(){
        val cursor3=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        Log.d("smscheck", "hi1")
        while (cursor3!!.moveToNext()) {
            var names =
                cursor3.getString(cursor3.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            var numberi =
                cursor3.getString(cursor3.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            var changed=""
            for (i in numberi){
                if(i!=' '){
                    changed+=i
                }
            }
            contactlist[changed] = names
            Log.d("smscheck2", "$changed and $names")
        }
        Log.d("smscheck", "hi5")

    }
//msglist has numbers that come from sms box and messages list//

    private fun refreshSMSbox() {
        val uri = Uri.parse("content://sms/inbox")
        var cursor = contentResolver.query(uri, null, null, null, null)
        while (cursor!!.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("address"))
            msg = cursor.getString(cursor.getColumnIndex("body"))
            Log.d("smscheckfrom", "$name")

            if(contactlist[name] !=null || (contactlist["${name.substring(3)}"]!=null&& contactlist[name] ==null)) {
                Log.d("smscheckfromextendedif","${contactlist[name]}")
                if(msglist[name]==null){
                    msglist[name]= mutableListOf(msg)
                }
                else{
                    msglist[name]!!.add(msg)
                }
            }
            else{
               unknownlist.add(
                   MsgObj(
                       "$name",
                       "$msg",
                       "$name"
                   )
               )
            }


        }
    }
    // Here we are basically distinguishing between +91 and not +91
    private fun preparelist(){
        for((key,value) in msglist){
            if(contactlist[key]!=null){
                knownlist.add(
                    MsgObj(
                        contactlist[key]!!,
                        value.first(),
                        key
                    )
                )
                Log.d("preparelist","${value.last()}")
            }
            if((contactlist["${key.substring(3)}"]!=null&& contactlist[key] ==null)){
                knownlist.add(
                    MsgObj(
                        contactlist["${key.substring(
                            3
                        )}"]!!, value.first(), key
                    )
                )
                Log.d("preparelist","${value.last()}")
            }
        }

    }
    public fun addsome(obj: MsgObj) {
        if(contactlist[obj.name]!=null || contactlist[obj.name.substring(3)]!=null){
            msglist[obj.name]?.add(0,obj.message)
            for (msgobj in knownlist){
                if(msgobj.num==obj.name){
                    msgobj.message=obj.message
                }
            }
            adapter.notifyItemChanged(0)
        }
        else {
            unknownlist.add(2, obj)
            adapter.notifyItemChanged(1)
        }
        view_pager.adapter=adapter


}
private fun prepareboxes(db:SQLiteDatabase){

for (msg in unknownlist){
    var bool=false
    for(cat in categorylist){
        var filterlist=
            AddFilter.getFilterList(
                cat,
                db
            )
        Log.d("filter",filterlist.toString())
        var count=0
        for(item in filterlist){
            if(msg.name.contains(item,true)){
                count++
            }
            else if(msg.message.contains(item,true)){
                count++
            }
            else{
                break
            }
        }
        Log.d("count filters","Done here  "+count.toString())
        if(count==filterlist.size){
            if(boxlist[cat]==null){
                boxlist[cat]= mutableListOf<MsgObj>(msg)
            }
            else{
                boxlist[cat]!!.add(msg)
            }
            Log.d("filterlist", boxlist[msg.name].toString())
            bool=true
        }
    }
    if(!bool){
        unknownlist2.add(msg)
    }
}
}





}
