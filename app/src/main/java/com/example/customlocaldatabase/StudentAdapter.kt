package com.example.customlocaldatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localdatabasemodule.YourDataModel

class StudentAdapter(private val studentList: List<YourDataModel>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewAge: TextView = itemView.findViewById(R.id.textViewAge)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = studentList[position]
        holder.textViewName.text = currentItem.name
        holder.textViewAge.text = "${currentItem.age}"
        holder.textViewEmail.text = currentItem.email
    }

    override fun getItemCount() = studentList.size
}