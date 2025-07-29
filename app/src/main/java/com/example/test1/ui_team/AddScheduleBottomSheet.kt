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
        "빨강" to "#FF6B6B",
        "파랑" to "#4DABF7",
        "보라" to "#B197FC",
        "초록" to "#51CF66",
        "회색" to "#AAAAAA"
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
            if (text.isEmpty()) return@setOnClickListener

            val selectedColor = colorMap[spinner.selectedItem as String] ?: "#AAAAAA"
            val data = mapOf("date" to date, "text" to text, "tagColor" to selectedColor)
            val path = if (isTeamMode) "teams/$teamId/schedules" else "users/$userId/schedules"

            FirebaseFirestore.getInstance().collection(path)
                .add(data)
                .addOnSuccessListener {
                    onScheduleAdded()
                    dismiss()
                }
        }
    }
}
