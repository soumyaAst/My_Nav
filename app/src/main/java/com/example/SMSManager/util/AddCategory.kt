package com.example.SMSManager.util

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SMSManager.Database
import com.example.SMSManager.R
import com.example.SMSManager.adapters.CategoryAdapter
import com.example.SMSManager.adapters.FilterAdapter
import kotlinx.android.synthetic.main.add_category.*

class AddCategory:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_category)
        val dbHelper= Database(this)
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
                Log.d("Row Created is ",newrow.toString())
                Toast.makeText(this,"$category - $filtertext added to database",Toast.LENGTH_SHORT).show()
                rv_cat_disp.adapter=
                    CategoryAdapter(
                        category,
                        1
                    )
                rv_cat_disp.layoutManager=LinearLayoutManager(this)
                rv_filter_disp.adapter=
                    FilterAdapter(
                        category,
                        AddFilter.getFilterList(
                            category,
                            db
                        )
                    )
                rv_filter_disp.layoutManager=LinearLayoutManager(this)
            }

        }

    }
}