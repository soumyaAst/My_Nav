package com.example.mynav

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SMSFilters.db"
    }
    private val CREATE_ENTRIES="CREATE TABLE "+"CATEGORIES_TABLE"+"( ID INTEGER PRIMARY KEY, "+"CATEGORY TEXT , "+"FILTER TEXT)"
    private val DROP_TABLE="DROP TABLE IF EXISTS CATEGORIES_TABLE"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
         db?.execSQL(DROP_TABLE)
    }
}