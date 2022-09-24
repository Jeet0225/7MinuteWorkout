package com.example.a7minuteworkout

import android.content.ContentProvider
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ExerciseAdapter(private var context: Context , private var itemList : ArrayList<ExerciseModel>)
                    : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(context).
        inflate(R.layout.item_exercise_status,
                    parent,
                    false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val items = itemList[position]
        holder.item.text = items.getId().toString()

        if(items.getIsSelected()){
            holder.item.background = ContextCompat.getDrawable(context,
                    R.drawable.white_recyclerview_ongoing_exercise_background)
            holder.item.setTextColor(Color.parseColor("#212121"))
        }else if (items.getIsCompleted()){
            holder.item.background = ContextCompat.getDrawable(context,
                    R.drawable.green_recyclerview_exercise_completed_background)
            holder.item.setTextColor(Color.parseColor("#FFFFFF"))
        }else {
            holder.item.background = ContextCompat.getDrawable(context,
                    R.drawable.background_image_item_recycler_view)
            holder.item.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val item : TextView = view.findViewById(R.id.tv_item)
    }
}