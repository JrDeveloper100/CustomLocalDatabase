package com.example.localdatabasemodule

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

data class YourDataModel(
    var id: Int = -1,  // Replace with the appropriate data type
    var name: String = "",
    var age: Int = 0,
    var email: String = ""
)


class DatabaseHelper(context: Context, DATABASE_NAME: String, DATABASE_VERSION: Int, private val TABLE_NAME: String) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_EMAIL = "email"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_AGE INTEGER," +
                "$COLUMN_EMAIL TEXT" +
                ")"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert data into the database
    fun insertData(name: String, age: Int, email: String): Long {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        values.put(COLUMN_EMAIL, email)

        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    // Retrieve data from the database
    fun getAllData(): ArrayList<YourDataModel> {
        val data = ArrayList<YourDataModel>()
        val db = readableDatabase
        val cursor: Cursor
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        } catch (e: SQLException) {
            // Handle any exceptions here
            return ArrayList()
        }

        var id: Int
        var name: String
        var age: Int
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
                email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                data.add(YourDataModel(id, name, age, email))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return data
    }

    // Update data in the database
    fun updateData(id: Int, name: String, age: Int, email: String): Int {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        values.put(COLUMN_EMAIL, email)

        val db = writableDatabase
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    // Delete data from the database
    fun deleteData(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteTable(){
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
}