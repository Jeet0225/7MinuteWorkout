package com.example.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context,factory : SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION = 1 //this database version
        private val DATABASE_NAME = "SevenMinutesWorkout.db" //name of database
        private val TABLE_HISTORY = "history" //table name
        private val COLUMN_ID = "_id" //Column id
        private val COLUMN_COMPLETED_DATE = "completed_date" //Column for storing date
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //create table history (_id integer primary key, completed date Text)
       val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY
        + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
               COLUMN_COMPLETED_DATE + " TEXT)")

        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXIST " + TABLE_HISTORY)
        onCreate(db)
    }
    //writing to the database
    fun addDate(date : String){
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE,date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY,null,values)
        db.close()
    }
    //reading to the database
    fun getAllCompleteDatesList() : ArrayList<String>{
        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY",null)

        while (cursor.moveToNext()){
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            list.add(dateValue)
        }
        db.close()
        return list
    }
}