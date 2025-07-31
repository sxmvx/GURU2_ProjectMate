package com.example.projectmate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.replace

class TeamSelectFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userGreeting: TextView
    private lateinit var textMyScheduleLabel: TextView

    // TODO: 로그인 사용자 정보로 대체
    private val currentUserId = "user123"
    private val currentUserName = "00"

    private val dummyTeams = listOf(
        Team("GURU2 - Team 19", "프론트엔드", "pink", "team19"),
        Team("GURU1 - Team 7", "백엔드", "gray", "team7")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewTeams)
        userGreeting = view.findViewById(R.id.textViewGreeting)
        textMyScheduleLabel = view.findViewById(R.id.textViewMyScheduleLabel)

        userGreeting.text = "${currentUserName}님, 안녕하세요!"

        // "내 일정" 클릭 시 → 개인 캘린더로 이동
        textMyScheduleLabel.setOnClickListener {
            val fragment = UserCalendarFragment.newInstance(currentUserId)
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
            }
        }

        // 팀 리스트 출력
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TeamListAdapter(dummyTeams) { team ->
            val fragment = TeamCalendarFragment.newInstance(team.id)
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
            }
        }
    }
}

// 팀 정보 모델
data class Team(val name: String, val role: String, val color: String, val id: String)
