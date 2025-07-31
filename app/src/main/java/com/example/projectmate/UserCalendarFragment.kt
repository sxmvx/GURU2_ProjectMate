package com.example.projectmate

import android.os.Bundle

class UserCalendarFragment : CalendarFragment() {

    companion object {
        fun newInstance(userId: String): UserCalendarFragment {
            val fragment = UserCalendarFragment()
            fragment.arguments = Bundle().apply {
                putString("userId", userId)
            }
            return fragment
        }
    }

    override fun getIsTeamMode(): Boolean = false
    override fun getOwnerId(): String = arguments?.getString("userId") ?: ""
    override fun getTeamId(): String = "" // 개인일정은 teamId 없음
}
