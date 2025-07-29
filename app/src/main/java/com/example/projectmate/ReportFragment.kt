package com.example.projectmate

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ReportFragment : Fragment() {
    private lateinit var commentAdapter: CommentAdapter
    private val commentList = mutableListOf<Comment>()
    private val db = FirebaseFirestore.getInstance()

    companion object {
        fun newInstance(nickname: String?, profileUrl: String?): ReportFragment {
            val fragment = ReportFragment()
            val args = Bundle().apply {
                putString("nickname", nickname)
                putString("profileUrl", profileUrl)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.commentRecyclerView)
        val commentEditText = view.findViewById<EditText>(R.id.commentEditText)
        val submitButton = view.findViewById<Button>(R.id.commentSubmitButton)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val dateTextView = view.findViewById<TextView>(R.id.dateTV)

        val nickname = arguments?.getString("nickname") ?: "사용자"
        val profileUrl = arguments?.getString("profileUrl")

        val nameTextView = view.findViewById<TextView>(R.id.nameTV)
        val profileImage = view.findViewById<ImageView>(R.id.profileImage)

        nameTextView.text = nickname

        Glide.with(this)
            .load(profileUrl ?: R.drawable.default_profile)
            .circleCrop()
            .into(profileImage)

        commentAdapter = CommentAdapter(commentList)
        recyclerView.adapter = commentAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        dateTextView.setOnClickListener {
            showReportDateListDialog()
        }

        // 🔸 댓글 불러오기
        db.collection("comments")
            .get()
            .addOnSuccessListener { result ->
                commentList.clear()
                for (document in result) {
                    val comment = document.toObject(Comment::class.java)
                    commentList.add(comment)
                }
                commentAdapter.notifyDataSetChanged()
                updateUIBasedOnComments(commentList, commentEditText, submitButton, saveButton)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "불러오기 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }

        submitButton.setOnClickListener {
            val commentText = commentEditText.text.toString().trim()
            if (commentText.isNotEmpty()) {
                val newComment = Comment(
                    name = "익명",  // 또는 사용자 닉네임
                    content = commentText,
                    timestamp = System.currentTimeMillis()
                )

                db.collection("comments")
                    .add(newComment)
                    .addOnSuccessListener {
                        commentList.add(newComment)
                        commentAdapter.notifyItemInserted(commentList.size - 1)
                        commentEditText.text.clear()
                        updateUIBasedOnComments(commentList, commentEditText, submitButton, saveButton)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "저장 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // 🔸 저장 버튼 클릭 시 동작 정의 (원하는 기능으로)
        saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "저장 완료!", Toast.LENGTH_SHORT).show()
            // TODO: 저장된 데이터 처리하거나 다음 화면으로 넘기기
        }
    }

    private fun showReportDateListDialog() {
        // Firestore에서 저장된 보고서 날짜 목록 불러오기
        db.collection("reports")
            .get()
            .addOnSuccessListener { result ->
                val dates = result.documents.map { it.id }.sortedDescending() // 날짜 문서 ID가 날짜라고 가정
                if (dates.isEmpty()) {
                    Toast.makeText(requireContext(), "저장된 보고서가 없습니다.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // AlertDialog로 목록 보여주기
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("보고서 날짜 선택")
                builder.setItems(dates.toTypedArray()) { _, which ->
                    val selectedDate = dates[which]
                    loadReportByDate(selectedDate)
                }
                builder.show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "보고서 목록 불러오기 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadReportByDate(date: String) {
        db.collection("reports")
            .document(date)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val report = document.toObject(Report::class.java)
                    if (report != null) {
                        // 날짜 표시 업데이트
                        view?.findViewById<TextView>(R.id.dateTV)?.text = date
                        // TODO: report 내용 (예: comment, todoList) 화면에 표시
                        // 예: 코멘트 리스트 업데이트, 투두리스트 업데이트 등
                        updateUIWithReport(report)
                    }
                } else {
                    Toast.makeText(requireContext(), "해당 날짜의 보고서가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "보고서 불러오기 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUIBasedOnComments(
        comments: List<Comment>,
        editText: EditText,
        submitButton: Button,
        saveButton: Button
    ) {
        if (comments.isEmpty()) {
            editText.visibility = View.VISIBLE
            submitButton.visibility = View.VISIBLE
            saveButton.visibility = View.GONE
        } else {
            editText.visibility = View.GONE
            submitButton.visibility = View.GONE
            saveButton.visibility = View.VISIBLE
        }
    }

    private fun updateUIWithReport(report: Report) {
        // 예시: 코멘트 리스트 갱신
        commentList.clear()
        commentList.addAll(report.comments) // Report 안에 comments: List<Comment> 있다고 가정
        commentAdapter.notifyDataSetChanged()

    }
}
