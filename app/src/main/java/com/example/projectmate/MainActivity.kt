package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Login"
    }

    object UserInfo {
        var nickname: String? = null
        var profileUrl: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //유저 정보 가져오기
        UserApiClient.instance.me { user, error ->
            if (user != null) {
                UserInfo.nickname = user.kakaoAccount?.profile?.nickname
                UserInfo.profileUrl = user.kakaoAccount?.profile?.profileImageUrl
            }
        }

        // 버튼 클릭 시 Todo_액티비티로 넘어감
        val TodoButton = findViewById<Button>(R.id.TodoButton)

        TodoButton.setOnClickListener {
            val intent = Intent(this, Todo_team::class.java)
            intent.putExtra("nickname", UserInfo.nickname)
            intent.putExtra("profileUrl", UserInfo.profileUrl)
            startActivity(intent)
        }

    }

}