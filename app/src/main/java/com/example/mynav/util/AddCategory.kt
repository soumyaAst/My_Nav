package com.example.mynav.util

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynav.Database
import com.example.mynav.R
import kotlinx.android.synthetic.main.add_category.*

class AddCategory:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_category)
        val dbHelper=Database(this)
        val db=dbHelper.writableDatabase
        btnFilter.setOnClickListener {
            Log.wtf("database","Start")
            var filtertext=filtertext.text.toString()
            var category=catfilter.text.toString()
            if(category.isEmpty()||filtertext.isEmpty()){
                Toast.makeText(this,"Fill all the Details Required",Toast.LENGTH_SHORT).show()
            }
            else{
               val values=ContentValues().apply {
                   put("CATEGORY",category)
                   put("FILTER",filtertext)
               }

                val newrow=db.insert("CATEGORIES_TABLE",null,values)
                Toast.makeText(this,"$category - $filtertext added to database",Toast.LENGTH_SHORT).show()
            }
        }
    }
}