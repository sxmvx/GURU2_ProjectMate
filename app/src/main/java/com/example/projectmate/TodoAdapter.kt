package com.example.projectmate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//투두 어댑터
class TodoAdapter(private var todoList: List<TodoItem>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    //기본 형태 구축
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoText: TextView = itemView.findViewById(R.id.todoText)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val colorDot: View = itemView.findViewById(R.id.colorDot)
    }

    //투두view에 표시할 각 줄 화면 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todoitem, parent, false)
        return TodoViewHolder(view)
    }

    //실제 데이터 꺼내서 item에 담음
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList[position]
        //각 데이터 화면에 반영
        holder.todoText.text = item.content
        holder.checkBox.isChecked = item.isDone
        holder.colorDot.setBackgroundColor(Color.parseColor(item.color))
    }

    //item 개수 반환 ㅡ> 투두view에 몇 줄 띄울지 결정
    override fun getItemCount(): Int = todoList.size
}