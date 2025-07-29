package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient

//홈 화면 클래스 (
class HomeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Login"
    }

    object UserInfo {
        var nickname: String? = null
        var profileUrl: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //유저 정보 가져오기
        UserApiClient.instance.me { user, error ->
            if (user != null) {
                UserInfo.nickname = user.kakaoAccount?.profile?.nickname
                UserInfo.profileUrl = user.kakaoAccount?.profile?.profileImageUrl
            }
        }

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


        // 버튼 클릭 시 Todo_액티비티로 넘어감
        val TodoButton = findViewById<Button>(R.id.TodoButton)
        val reportButton = findViewById<ImageButton>(R.id.reportBtn)

        TodoButton.setOnClickListener {
            val intent = Intent(this, Todo_team::class.java)
            intent.putExtra("nickname", UserInfo.nickname)
            intent.putExtra("profileUrl", UserInfo.profileUrl)
            startActivity(intent)
        }

        reportButton.setOnClickListener {
            val nickname = UserInfo.nickname
            val profileUrl = UserInfo.profileUrl

            val fragment = ReportFragment.newInstance(nickname, profileUrl)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

}