package com.example.projectmate

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

        calendarView = view.findViewById(R.id.calendarView)
        recyclerView = view.findViewById(R.id.recyclerViewSchedule)
        textViewSelectedDate = view.findViewById(R.id.textViewSelectedDate)

        val profileFrame = view.findViewById<View>(R.id.profileFrame)
        val textViewGreeting = view.findViewById<TextView>(R.id.textViewGreeting)

        if (getIsTeamMode()) {
            textViewGreeting.text = "${getTeamId()} Calendar"
            profileFrame.visibility = View.GONE
        } else {
            textViewGreeting.text = "${getOwnerId()}님, 안녕하세요!"
            profileFrame.visibility = View.VISIBLE
        }

        scheduleAdapter = ScheduleAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
        }

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
                    decorateCalendarWithDots(clickedDate)
                    calendarView.invalidateDecorators()
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

    private fun decorateCalendarWithDots(date: String) {
        calendarView.removeDecorators()

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

                val first = result.documents.first()
                val color = first.getString("tagColor") ?: "#AAAAAA"
                try {
                    val split = date.split("-")
                    val year = split[0].toInt()
                    val month = split[1].toInt()
                    val day = split[2].toInt()
                    val dayObj = CalendarDay.from(year, month, day)
                    val colorInt = android.graphics.Color.parseColor(color)
                    calendarView.addDecorator(ScheduleDotDecorator(listOf(dayObj), colorInt))
                    calendarView.invalidateDecorators()
                } catch (e: Exception) {
                    Log.e("DotError", "decorateCalendarWithDots - $date", e)
                }
            }
    }
}
