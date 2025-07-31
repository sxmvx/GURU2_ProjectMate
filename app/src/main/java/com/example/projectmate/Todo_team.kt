package com.example.projectmate

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.google.firebase.database.Query
//import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar



class Todo_team : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_team)

        //리사이클뷰를 xml, 어댑터와 연결
        recyclerView = findViewById(R.id.todoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        todoAdapter = TodoAdapter()
        recyclerView.adapter = todoAdapter

        // 임시데이터
        val todoList = listOf(
            TodoItem("7.31", "보고서 작성", false, "#FF6F61"),
            TodoItem("7.31", "회의 준비", false, "#6A5ACD"),
            TodoItem("8.1", "과제 제출", false, "#20B2AA"),
            TodoItem("8.3", "팀 회의", false, "#FFB347"),
            TodoItem("8.8", "중간 점검", false, "#6A5ACD"),
            TodoItem("8.21", "발표 자료 준비", false, "#6495ED"),
            TodoItem("9.9", "팀 회의", false, "#6A5ACD"),
            TodoItem("9.18", "최종 점검", false, "#00CED1"),
            TodoItem("9.21", "프로젝트 제출", false, "#8A2BE2")
        )


        val grouped = todoList.groupBy { it.date }
        val displayList = mutableListOf<TodoDisplayItem>()

        for ((date, items) in grouped) {
            displayList.add(TodoDisplayItem.DateHeader(date))
            for (item in items) {
                displayList.add(TodoDisplayItem.TodoData(item))
            }
        }
        todoAdapter.setDisplayList(todoList)

        //유저 닉네임과 프로필 사진 받아옴
        val nickname = intent.getStringExtra("nickname")
        val profileUrl = intent.getStringExtra("profileUrl")

        val nicknameText = findViewById<TextView>(R.id.nicknameTextView)
        val profileImage = findViewById<ImageView>(R.id.profileImageView)

        nicknameText.text = nickname ?: "사용자" //유저 카톡 닉네임 가져오기 : null일 경우 "사용자"로 설정

        if (!profileUrl.isNullOrEmpty()) {
            // 유저 프로필이 존재하면 해당 이미지 불러오기
            Glide.with(this)
                .load(profileUrl)
                .circleCrop()
                .into(profileImage)
        } else {
            // null일 경우, 기본 이미지 보여주기
            Glide.with(this)
                .load(R.drawable.default_profile)
                .circleCrop()
                .into(profileImage)
        }
    }
}

/* 더미 데이터(임시) 실행을 위해 파이어베이스 부분 주석처리하였음

        //파이어스토어에서 할 일 불러오기
       loadTodosFromFirestore()
    }

    private fun loadTodosFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("todo_team")
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val todoList = result.map { it.toObject(TodoItem::class.java) }
               todoAdapter.setDisplayList(todoList)
            }
            .addOnFailureListener { e ->
                Log.e("TodoActivity", "Error loading todos", e)
            }
    }
}

 */