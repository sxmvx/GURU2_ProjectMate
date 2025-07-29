package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import org.checkerframework.framework.qual.FromByteCode

class JoinTeam : AppCompatActivity() {

    private lateinit var editTextTeamCode: EditText
    private lateinit var buttonJoinTeam: Button

    private var db = Firebase.firestore // 파이어베이스 firestore 참조

    // 현재 로그인된 사용자의 UID
    private val currentUserUID: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_team)

        editTextTeamCode = findViewById<EditText>(R.id.editTextTeamCode)
        buttonJoinTeam = findViewById<Button>(R.id.buttonJoinTeam)

        // 버튼 클릭 시
        buttonJoinTeam.setOnClickListener {
            val teamCode = editTextTeamCode.text.toString().trim()

            // 팀 코드가 비어 있을 경우 알림
            if (teamCode.isEmpty()) {
                Toast.makeText(this, "팀 코드를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = currentUserUID
            if (uid == null) {
                Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 입력된 팀 코드가 firestore에 존재하는지 확인
            db.collection("teams").document(teamCode).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val teamName = document.getString("name") ?: "팀"

                        // 해당 팀에 유저 UID 추가
                        db.collection("teams").document(teamCode)
                            .update("members.$uid", true)


                        // 유저의 joinedTeams에 팀 등록
                        db.collection("users"). document(uid)
                            .collection("joinedTeams").document(teamCode)
                            .set(mapOf("joinedAt" to Timestamp.now()))

                        //2. 유저가 가입한 팀 목록에 팀 코드 추가
                        val userTeamsRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("teams")
                        userTeamsRef.child(teamCode).setValue(true)


                        Toast.makeText(this, "\"$teamName\" 팀에 참여했습니다!", Toast.LENGTH_SHORT).show()

                        // 가입 완료 후 ExistingTeam 화면으로 이동
                        val intent = Intent(this@JoinTeam, ExistingTeam::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish() //현재 액티비티 종료

                    } else {
                        Toast.makeText(this@JoinTeam, "해당 팀 코드가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }
}