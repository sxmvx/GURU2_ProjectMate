package com.example.test1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.ui_team.TeamSelectFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 진입 선택 화면: TeamSelectFragment로 시작
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TeamSelectFragment())
                .commit()
        }
    }
}
