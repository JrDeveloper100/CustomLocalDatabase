package com.example.customlocaldatabase

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localdatabasemodule.DatabaseHelper
import com.example.localdatabasemodule.YourDataModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var et_name : EditText
    private lateinit var et_age : EditText
    private lateinit var et_email : EditText
    private lateinit var btnInsert : Button
    private lateinit var data : ArrayList<YourDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_name = findViewById(R.id.et_name)
        et_age = findViewById(R.id.et_age)
        et_email = findViewById(R.id.et_email)
        btnInsert = findViewById(R.id.btnInsert)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val dbHelper = DatabaseHelper(this, "Student_Database", 1,"Student_Data")
        val db = dbHelper.writableDatabase
        btnInsert.setOnClickListener {
            et_age.text.toString().toInt()
            dbHelper.insertData(et_name.text.toString(),et_age.text.toString().toInt(),et_email.text.toString())
            data = dbHelper.getAllData()
            recyclerView.adapter = StudentAdapter(data)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        data = dbHelper.getAllData()

        if (data.isNotEmpty()){
//            Toast.makeText(this,"Data Inserted",Toast.LENGTH_SHORT).show()
            recyclerView.adapter = StudentAdapter(data)
            recyclerView.setHasFixedSize(true)
        }else{
            Toast.makeText(this,"There is no Record in Database Please Insert",Toast.LENGTH_SHORT).show()
        }


    }
}