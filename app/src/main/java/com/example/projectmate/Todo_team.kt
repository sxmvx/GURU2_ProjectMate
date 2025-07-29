package com.example.projectmate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Todo_team : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_team)

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