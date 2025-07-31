package com.example.projectmate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 날짜 헤더 뷰
    class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.dateHeaderText)
    }

    // 할 일 뷰
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoText: TextView = itemView.findViewById(R.id.todoText)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val colorDot: View = itemView.findViewById(R.id.colorDot)
    }

    // 각 줄 화면 구성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.todoitem_date_header, parent, false)
            DateHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.todoitem, parent, false)
            TodoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = displayList[position]) {
            is TodoDisplayItem.DateHeader -> {
                val viewHolder = holder as DateHeaderViewHolder
                viewHolder.dateText.text = item.date
            }
            is TodoDisplayItem.TodoData -> {
                val viewHolder = holder as TodoViewHolder
                val todo = item.todo
                viewHolder.todoText.text = todo.content
                viewHolder.checkBox.isChecked = todo.isDone
                viewHolder.colorDot.setBackgroundColor(Color.parseColor(todo.color))
            }
        }
    }

    override fun getItemCount(): Int = displayList.size

    //각 항목 타입 구분
    override fun getItemViewType(position: Int): Int {
        return when (displayList[position]) {
            is TodoDisplayItem.DateHeader -> 0
            is TodoDisplayItem.TodoData -> 1
        }
    }

    private var displayList = listOf<TodoDisplayItem>()

    fun setDisplayList(todoList: List<TodoItem>) {
        val grouped = todoList.groupBy { it.date }
        val sortedDates = grouped.keys.sorted()

        val result = mutableListOf<TodoDisplayItem>()
        for (date in sortedDates) {
            result.add(TodoDisplayItem.DateHeader(date))
            grouped[date]?.forEach { todo ->
                result.add(TodoDisplayItem.TodoData(todo))
            }
        }

        displayList = result
        notifyDataSetChanged()
    }
}
