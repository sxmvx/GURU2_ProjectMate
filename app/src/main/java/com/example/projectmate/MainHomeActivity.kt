package com.example.projectmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
=======

>>>>>>> aa15941 (캘린더 + Firebase)

class MainHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

<<<<<<< HEAD
        // 진입 선택 화면: TeamSelectFragment로 시작
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TeamSelectFragment())
                .commit()
        }
=======
        // 👇 하나만 선택해서 사용해! (둘 다 쓰면 마지막만 보임)

        // ✅ 개인 일정 화면으로 진입
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PersonalCalendarFragment())
            .commit()

        // ✅ 팀 일정 화면으로 진입 (예시로 teamId = "team19")
        /*
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TeamCalendarFragment("team19"))
            .commit()
        */
>>>>>>> aa15941 (캘린더 + Firebase)
    }
}
