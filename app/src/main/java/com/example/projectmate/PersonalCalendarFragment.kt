package com.example.projectmate

class PersonalCalendarFragment : CalendarFragment() {
    override fun getIsTeamMode(): Boolean = false
    override fun getOwnerId(): String = "testUser" // 🔧 실제 로그인 유저로 교체 가능
    override fun getTeamId(): String = ""
}
