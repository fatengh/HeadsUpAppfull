package com.example.headsupapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract.Helpers.insert

class DBHlpr(context: Context?) : SQLiteOpenHelper(context, "headsUp.db", null, 1) {

    var sqlDB: SQLiteDatabase = writableDatabase // create database variable
    var sqlDBR: SQLiteDatabase = readableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) { // cteate table
            db.execSQL("create table Celebrity (Name text, Taboo1 text, Taboo2 text, Taboo3 text, Pk INTEGER PRIMARY KEY AUTOINCREMENT )")
            //(val name: String, val taboo1: String, val taboo2: String, val taboo3: String, val pk: Int)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    fun saveData(
        name: String,
        taboo1: String,
        taboo2: String,
        taboo3: String
    ): Long { // to return : Long
        // add data to ContentValues
        val cv = ContentValues()
        cv.put("Name", name)
        cv.put("Taboo1", taboo1)
        cv.put("Taboo2", taboo2)
        cv.put("Taboo3", taboo3)

        // add to database 3 para (name of table , null , contentValuse)
        return sqlDB.insert("Celebrity", null, cv)
        // to check data saved or not
        // var statuse= sqlDB.insert("student",null , cv)
        // return statuse
        // return -1 not saved
    }

    fun retrive(): ArrayList<Celebrity> {
        val celebrities = ArrayList<Celebrity>()
        val cursor: Cursor = sqlDBR.query("Celebrity", null, null, null, null, null, null)
        if (cursor.moveToFirst()) { // first step
            var pk = cursor.getInt(cursor.getColumnIndex("Pk"))
            var name = cursor.getString(cursor.getColumnIndex("Name"))
            var taboo1 = cursor.getString(cursor.getColumnIndex("Taboo1"))
            var taboo2 = cursor.getString(cursor.getColumnIndex("Taboo2"))
            var taboo3 = cursor.getString(cursor.getColumnIndex("Taboo3"))
            celebrities.add(Celebrity( name, taboo1, taboo2, taboo3, pk))
            while (cursor.moveToNext()) { // dont stop until finish
                pk = cursor.getInt(cursor.getColumnIndex("Pk"))
                name = cursor.getString(cursor.getColumnIndex("Name"))
                taboo1 = cursor.getString(cursor.getColumnIndex("Taboo1"))
                taboo2 = cursor.getString(cursor.getColumnIndex("Taboo2"))
                taboo3 = cursor.getString(cursor.getColumnIndex("Taboo3"))
                celebrities.add(Celebrity( name, taboo1, taboo2, taboo3, pk))
            }
        }
        cursor.close()
        return celebrities

    }
}
