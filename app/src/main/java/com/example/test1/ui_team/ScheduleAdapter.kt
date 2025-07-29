package com.example.test1.ui_team

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private val items = mutableListOf<ScheduleItem>()

    fun submitList(newList: List<ScheduleItem>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagView: View = view.findViewById(R.id.viewTag)
        val textView: TextView = view.findViewById(R.id.textSchedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.text
        holder.tagView.setBackgroundColor(Color.parseColor(item.tagColor))
    }

    override fun getItemCount(): Int = items.size
}
