package com.example.SMSManager.util

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SMSManager.Database
import com.example.SMSManager.R
import com.example.SMSManager.adapters.FilterAdapter
import kotlinx.android.synthetic.main.add_filter.*

public class AddFilter: AppCompatActivity() {
    companion object{
        public fun getCategories(spinlist:MutableList<String>,db:SQLiteDatabase):MutableList<String>{
            var projection = arrayOf("CATEGORY")
            var cursor = db.query(true,"CATEGORIES_TABLE", projection, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                spinlist.add(cursor.getString(cursor.getColumnIndex("CATEGORY")))
            }
            Log.d("database", spinlist.toString())
            cursor.close()
            return spinlist
        }
        public fun getFilterList(cat:String, db:SQLiteDatabase): ArrayList<String> {
            Log.d("database",cat)
            var cursor2 = db.query(
                "CATEGORIES_TABLE",
                null,
                "CATEGORY= ?",
                arrayOf(cat),
                null,
                null,
                null
            )
            var filterlist = arrayListOf<String>()
            while (cursor2.moveToNext()) {
                filterlist.add(cursor2.getString(cursor2.getColumnIndex("FILTER")))
            }
            Log.d("database", filterlist.toString())
            cursor2.close()
            return filterlist
        }
    }
    private var selectedpos=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_filter)

        val dbHelper = Database(this)
        val db = dbHelper.writableDatabase
        Log.d("database", "hello")
        var spinlist = arrayListOf<String>()
        spinlist.add("SELECT SMS-BOX")
        spinlist= getCategories(
            spinlist,
            db
        ) as ArrayList<String>
//        var cursor = db.query(true,"CATEGORIES_TABLE", projection, null, null, null, null, null, null)
//        while (cursor.moveToNext()) {
//            spinlist.add(cursor.getString(cursor.getColumnIndex("CATEGORY")))
//        }
//        Log.d("database", spinlist.toString())
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, spinlist
        )
        val cont=this
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cat_spinner.adapter=adapter
        cat_spinner.prompt = "Select Category"
        cat_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedpos=position
                if(position!=0) {
                    var cat = spinlist[position]
                    var filter=
                        getFilterList(
                            cat,
                            db
                        )
                    var adapter=
                        FilterAdapter(
                            spinlist[position],
                            filter
                        )
                    rv_filter.adapter=adapter
                    rv_filter.layoutManager=LinearLayoutManager(cont)

                }
            }


        }
        add_filter_btn.setOnClickListener {
            var newstr=add_filter_text.text.toString()
            if (newstr.isNotEmpty() && selectedpos!=0) {
                var cat = spinlist[selectedpos]
                val values = ContentValues().apply {
                    put("CATEGORY", cat)
                    put("FILTER", newstr)
                }

                val newrow = db.insert("CATEGORIES_TABLE", null, values)
                var filter =
                    getFilterList(
                        cat,
                        db
                    )
                var adapter = FilterAdapter(
                    spinlist[selectedpos],
                    filter
                )
                rv_filter.adapter = adapter
                rv_filter.layoutManager = LinearLayoutManager(cont)
                add_filter_text.text=null
            } else {
                Toast.makeText(cont,"ADD SOME TEXT",Toast.LENGTH_SHORT).show()
            }
        }

    }

}