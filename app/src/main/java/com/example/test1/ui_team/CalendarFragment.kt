package com.example.test1.ui_team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R
import com.google.firebase.firestore.FirebaseFirestore
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Locale

abstract class CalendarFragment : Fragment() {

    // 🔓 상속 클래스에서 이 3개만 오버라이드
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

        calendarView = view.findViewById(R.id.calendarView)
        recyclerView = view.findViewById(R.id.recyclerViewSchedules)
        textViewSelectedDate = view.findViewById(R.id.textViewSelectedDate)

        scheduleAdapter = ScheduleAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
        }

        calendarView.setOnDateChangedListener { _, date, _ ->
            val clickedDate = String.format(
                Locale.getDefault(),
                "%04d-%02d-%02d",
                date.year, date.month + 1, date.day
            )

            // 날짜 표시
            textViewSelectedDate.text = "${date.month + 1}.${date.day}"

            // 일정 추가
            AddScheduleBottomSheet(
                date = clickedDate,
                isTeamMode = getIsTeamMode(),
                userId = getOwnerId(),
                teamId = getTeamId(),
                onScheduleAdded = {
                    loadSchedules(clickedDate)
                    decorateCalendarWithDots(clickedDate)
                }
            ).show(parentFragmentManager, "AddSchedule")

            loadSchedules(clickedDate)
            decorateCalendarWithDots(clickedDate)
        }
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
                        text = doc.getString("text") ?: "",
                        tagColor = doc.getString("tagColor") ?: "#AAAAAA"
                    )
                }
                scheduleAdapter.submitList(list)
            }
            .addOnFailureListener { it.printStackTrace() }
    }

    private fun decorateCalendarWithDots(date: String) {
        val path = if (getIsTeamMode()) {
            "teams/${getTeamId()}/schedules"
        } else {
            "users/${getOwnerId()}/schedules"
        }

        db.collection(path)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) return@addOnSuccessListener

                val day = CalendarDay.from(
                    date.split("-")[0].toInt(),
                    date.split("-")[1].toInt(),
                    date.split("-")[2].toInt()
                )

                val colorHex = result.documents.first().getString("tagColor") ?: "#AAAAAA"
                val colorInt = android.graphics.Color.parseColor(colorHex)

                calendarView.addDecorator(ScheduleDotDecorator(listOf(day), colorInt))
            }
    }
}
