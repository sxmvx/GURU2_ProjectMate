package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kakao.sdk.user.UserApiClient

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //val kakaoLoginBtn = findViewById<Button>(R.id.kakao_login_button) // 버튼 ID 일치해야 함

        //kakaoLoginBtn.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("KakaoLogin", "로그인 실패", error)
                    } else if (token != null) {
                        Log.d("KakaoLogin", "로그인 성공 ${token.accessToken}")
                        goToMain()
                    }
                }
            } else {
                Toast.makeText(this, "카카오톡 미설치됨", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}