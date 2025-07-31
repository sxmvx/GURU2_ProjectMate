package com.example.projectmate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private var schedules: List<ScheduleItem> = emptyList()

    fun setSchedules(newList: List<ScheduleItem>) {
        schedules = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentText: TextView = view.findViewById(R.id.textScheduleContent)
        val tagView: View = view.findViewById(R.id.viewScheduleTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = schedules[position]
        holder.contentText.text = item.content
        try {
            holder.tagView.setBackgroundColor(Color.parseColor(item.tagColor))
        } catch (e: Exception) {
            holder.tagView.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun getItemCount(): Int = schedules.size
}
