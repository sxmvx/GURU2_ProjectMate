package com.example.projectmate

import android.os.Bundle

class TeamCalendarFragment : CalendarFragment() {

    private var teamId: String = ""

    companion object {
        fun newInstance(teamId: String): TeamCalendarFragment {
            val fragment = TeamCalendarFragment()
            fragment.arguments = Bundle().apply {
                putString("teamId", teamId)
            }
            return fragment
        }
    }

    override fun getIsTeamMode(): Boolean = true
    override fun getOwnerId(): String = "user123" // TODO: 로그인 사용자로 교체
    override fun getTeamId(): String = arguments?.getString("teamId") ?: ""
}
