package com.example.test1.ui_team

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R
import com.google.firebase.firestore.FirebaseFirestore
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Locale

abstract class CalendarFragment : Fragment() {

    abstract fun getIsTeamMode(): Boolean
    abstract fun getOwnerId(): String
    abstract fun getTeamId(): String

    private lateinit var calendarView: MaterialCalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var textViewSelectedDate: TextView


    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("CalendarDebug", "onViewCreated - isTeam: ${getIsTeamMode()}, userId: ${getOwnerId()}, teamId: ${getTeamId()}")

        calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSchedule)
        textViewSelectedDate = view.findViewById<TextView>(R.id.textViewSelectedDate)

        // [추가] 상단 프로필/텍스트뷰 구분
        // onViewCreated 내에 추가
        val profileFrame = view.findViewById<View>(R.id.profileFrame)
        val textViewGreeting = view.findViewById<TextView>(R.id.textViewGreeting)

        if (getIsTeamMode()) {
            textViewGreeting.text = "${getTeamId()} Calendar"
            profileFrame.visibility = View.GONE
        } else {
            textViewGreeting.text = "${getOwnerId()}님, 안녕하세요!"
            profileFrame.visibility = View.VISIBLE
        }


        // 🔹 일정 리스트 초기화 (빈 리스트로 시작)
        scheduleAdapter = ScheduleAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
        }

        // 🔹 날짜 선택 이벤트 처리
        calendarView.setOnDateChangedListener { _, date, _ ->
            val clickedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", date.year, date.month + 1, date.day)
            textViewSelectedDate.text = "${date.month + 1}.${date.day}"

            AddScheduleBottomSheet(
                date = clickedDate,
                isTeamMode = getIsTeamMode(),
                userId = getOwnerId(),
                teamId = getTeamId(),
                onScheduleAdded = {
                    loadSchedules(clickedDate)
                    decorateAllScheduleDots()
                    calendarView.invalidateDecorators()
                }
            ).show(parentFragmentManager, "AddSchedule")

            loadSchedules(clickedDate)
        }

        // 🔹 초기 dot 표시
        decorateAllScheduleDots()
        calendarView.invalidateDecorators()
    }


    private fun loadSchedules(date: String) {
        val path = if (getIsTeamMode()) {
            "teams/${getTeamId()}/schedules"
        } else {
            "users/${getOwnerId()}/schedules"
        }

        db.collection(path)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.map { doc ->
                    ScheduleItem(
                        content = doc.getString("text") ?: "",
                        tagColor = doc.getString("tagColor") ?: "#AAAAAA"
                    )
                }
                scheduleAdapter.setSchedules(list)
            }
            .addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(requireContext(), "일정 불러오기 실패", Toast.LENGTH_SHORT).show()
            }
    }



    private fun decorateAllScheduleDots() {
        // **중복 decorator 방지: 모두 clear**
        calendarView.removeDecorators()

        val path = if (getIsTeamMode()) {
            "teams/${getTeamId()}/schedules"
        } else {
            "users/${getOwnerId()}/schedules"
        }

        db.collection(path)
            .get()
            .addOnSuccessListener { result ->
                val grouped = result.documents
                    .filter { doc ->
                        val text = doc.getString("text")
                        !text.isNullOrBlank()
                    }
                    .groupBy { it.getString("date") }

                for ((date, docs) in grouped) {
                    // ---------- [로그로 모든 값 추적!] ----------
                    Log.d("DotCheck", "Firestore date string: $date, docs size: ${docs.size}")
                    try {
                        val split = date!!.split("-")
                        val year = split[0].toInt()
                        val month = split[1].toInt() // 반드시 1~12
                        val dayOfMonth = split[2].toInt()
                        val day = CalendarDay.from(year, month, dayOfMonth)
                        Log.d("DotCheck", "addDecorator: $year-$month-$dayOfMonth, CalendarDay=$day")

                        val colorHex = docs.first().getString("tagColor") ?: "#AAAAAA"
                        val colorInt = android.graphics.Color.parseColor(colorHex)

                        calendarView.addDecorator(ScheduleDotDecorator(listOf(day), colorInt))
                    } catch (e: Exception) {
                        Log.e("DotCheck", "dot error for date=$date", e)
                    }
                }
                // **dot 갱신 확실하게**
                calendarView.invalidateDecorators()
            }
    }
}
