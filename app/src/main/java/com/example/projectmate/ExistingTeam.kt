package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ExistingTeam : AppCompatActivity() {

    private lateinit var teamListView: ListView // 팀 리스트
    private lateinit var emptyTextView: TextView // 팀 없을 경우 표시
    private lateinit var database: DatabaseReference // Firebase RD 참조
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
        database = FirebaseDatabase.getInstance().reference

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

    // 현재 로그인 된 사용자의 팀 목록을 Firebase에서 불러오는 함수
    private fun loadUserTeams(adapter: ArrayAdapter<String>) {
        val uid = currentUserUID
        if (uid == null) {
            Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // 유저가 가입한 팀 ID 리스트 읽기
        database.child("users").child(uid).child("teams")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        //가입된 팀이 없을 경우 메시지 표시
                        emptyTextView.text = "가입된 팀이 없습니다."
                        emptyTextView.visibility = TextView.VISIBLE
                        return
                    }

                    //이전 데이터 초기화
                    teamList.clear()
                    teamIdList.clear()

                    //유저가 가입한 팀 ID 하나씩 처리
                    for (teamSnapshot in snapshot.children) {
                        val teamId = teamSnapshot.key ?: continue

                        //각 팀 ID에 해당하는 팀 이름 불러옴
                        database.child("teams").child(teamId).child("name")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(nameSnapshot: DataSnapshot) {
                                    val teamName = nameSnapshot.getValue(String::class.java)
                                    if (teamName != null) {
                                        //팀 이름과 ID를 리스트에 추가
                                        teamList.add(teamName)
                                        teamIdList.add(teamId)
                                        adapter.notifyDataSetChanged() //리스트뷰 갱신
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.e("Firebase", "팀 이름 불러오기 실패: ${error.message}")
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "팀 목록 불러오기 실패: ${error.message}")
                }
            })
    }
}