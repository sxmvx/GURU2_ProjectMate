package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ExistingTeam : AppCompatActivity() {

    private lateinit var teamListView: ListView // 팀 리스트
    private lateinit var emptyTextView: TextView // 팀 없을 경우 표시
    private val db = FirebaseFirestore.getInstance() // Firestore 인스턴스
    private val teamList = mutableListOf<String>() // 화면에 표시할 팀 이름 리스트
    private val teamIdList = mutableListOf<String>() // 내부적으로 팀 ID 저장

    // 현재 로그인된 유저의 UID(FirebaseAuth에서 가져옴)
    private val currentUserUID: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_existing_team)

        teamListView = findViewById(R.id.teamListView)
        emptyTextView = findViewById(R.id.emptyTextView)

        // 리스트 어댑터
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, teamList)
        teamListView.adapter = adapter

        // 팀 목록 로드
        loadUserTeams(adapter)

        //팀 클릭 시 해당 팀의 메인 화면으로 이동
        teamListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTeamId = teamIdList[position]
            val intent = Intent(this, TeamMainActivity::class.java) // 나중에 팀 메인화면 파일로 변경
            intent.putExtra("teamId", selectedTeamId)
            startActivity(intent)
        }
    }

    // Firestore에서 유저가 가입한 팀 목록 불러오기
    private fun loadUserTeams(adapter: ArrayAdapter<String>) {
        val uid = currentUserUID
        if (uid == null) {
            Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // Firestore에서 팀 ID 목록 불러오기
        db.collection("users").document(uid).collection("joinedTeams")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    emptyTextView.text = "가입된 팀이 없습니다."
                    emptyTextView.visibility = TextView.VISIBLE
                    return@addOnSuccessListener
                }

                //이전 데이터 초기화
                teamList.clear()
                teamIdList.clear()

                for (doc in documents) {
                    val teamId = doc.id

                    // Firestore: teams/{teamId}/name 필드 불러오기
                    db.collection("teams").document(teamId).get()
                        .addOnSuccessListener { teamDoc ->
                            val teamName = teamDoc.getString("name")
                            if (teamName != null) {
                                teamList.add(teamName)
                                teamIdList.add(teamId)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firebase", "팀 이름 불러오기 실패: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "팀 목록 불러오기 실패: ${e.message}")
            }
    }
}
