package com.example.projectmate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class CreateTeam : AppCompatActivity() {

    private lateinit var TeamNameEditText: EditText
    private lateinit var createButton: Button
    private lateinit var CreatedCode: TextView
    private lateinit var codeCopyButton: Button

    // 생성된 코드
    private var generatedCode: String? = null

    // 현재 로그인한 사용자 UID
    private val currentUserUID: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)

        TeamNameEditText = findViewById(R.id.TeamNameEditText)
        createButton = findViewById(R.id.createButton)
        CreatedCode = findViewById(R.id.CreatedCode)
        codeCopyButton = findViewById(R.id.copyCodeButton)

        val db = Firebase.firestore

        // 버튼 클릭 시
        createButton.setOnClickListener {
            val teamName = TeamNameEditText.text.toString().trim()

            if (teamName.isEmpty()){
                Toast.makeText(this, "팀 이름을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = currentUserUID
            if (uid == null) {
                Toast.makeText(this, "로그인 정보가 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 6자리 랜덤 대문자 + 숫자 팀 코드 생성
            val code = (1..6).map {
                (('A'..'Z') + ('0'..'9')).random()
            }.joinToString("")
            generatedCode = code // 복사 기능 작동하도록 코드 저장

            // 팀 정보 맵 생성 (이름, 코드 포함)
            val teamInfo = mapOf(
                "name" to teamName,
                "code" to code,
                // 멤버 목록 초기화 : 팀장(생성자) UID 추가
                "members" to listOf(uid) // 멤버 -> 리스트로
            )

            // Firebstore에 팀 생성
            db.collection("teams").document(code)
                .set(teamInfo)
                .addOnSuccessListener {
                    CreatedCode.text = code
                    Toast.makeText(this, "팀 코드 생성 완료!", Toast.LENGTH_SHORT).show()

                    // 사용자 정보에도 팀 등록
                    db.collection("users").document(uid)
                        .collection("joinedTeams").document(code)
                        .set(mapOf("joinedAt" to Timestamp.now()))

                }
                .addOnFailureListener {
                    Toast.makeText(this, "팀 생성 실패", Toast.LENGTH_SHORT).show()
                }
        }

        // 복사하기 버튼 클릭 시
        codeCopyButton.setOnClickListener {
            val code = generatedCode
            if (code.isNullOrEmpty()) {
                Toast.makeText(this, "복사할 코드가 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 클립보드에 복사
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("Team code", code)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "팀 코드가 복사되었습니다!", Toast.LENGTH_SHORT).show()
        }

    }
}