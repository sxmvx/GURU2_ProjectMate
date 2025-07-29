package com.example.test1.ui_team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.test1.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore

class AddScheduleBottomSheet(
    private val date: String,
    private val isTeamMode: Boolean,
    private val userId: String = "testUser",
    private val teamId: String = "testTeam",
    private val onScheduleAdded: () -> Unit
) : BottomSheetDialogFragment() {

    private val colorMap = mapOf(
        "핑크" to "#F5B2B0",
        "그레이" to "#DBDBDB",
        "하늘" to "#B9E1EF",
        "다홍" to "#F6896B",
        "라벤더" to "#B9A6D6"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottomsheet_add_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val editText = view.findViewById<EditText>(R.id.editTextSchedule)
        val spinner = view.findViewById<Spinner>(R.id.spinnerColor)
        val button = view.findViewById<Button>(R.id.buttonAddSchedule)

        val colorNames = colorMap.keys.toList()
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorNames).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        button.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(requireContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val selectedColor = colorMap[spinner.selectedItem as String] ?: "#AAAAAA"
            val data = mapOf("date" to date, "text" to text, "tagColor" to selectedColor)
            val path = if (isTeamMode) "teams/$teamId/schedules" else "users/$userId/schedules"

            FirebaseFirestore.getInstance().collection(path)
                .add(data)
                .addOnSuccessListener {
                    onScheduleAdded()
                    // 이 코드를 항상 UI 스레드에서 호출
                    dismissAllowingStateLoss() // ← 이 함수는 dismiss()보다 더 강제적으로 닫음!
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "일정 저장에 실패했어요.", Toast.LENGTH_SHORT).show()
                    it.printStackTrace()
                    dismissAllowingStateLoss() // ← 실패해도 무조건 닫기
                }
        }


    }
}
