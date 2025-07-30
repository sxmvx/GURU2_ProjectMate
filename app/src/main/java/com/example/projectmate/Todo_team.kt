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
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class Todo_team : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_team)


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


        //리사이클뷰를 xml, 어댑터와 연결
        recyclerView = findViewById(R.id.todoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoAdapter = TodoAdapter(todoList)
        recyclerView.adapter = todoAdapter

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