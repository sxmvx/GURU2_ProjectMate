package com.android.projectmate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReportFragment : Fragment() {
    private lateinit var commentAdapter: CommentAdapter
    private val commentList = mutableListOf<Comment>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.commentRecyclerView)
        val commentEditText = view.findViewById<EditText>(R.id.commentEditText)
        val submitButton = view.findViewById<Button>(R.id.commentSubmitButton)

        commentAdapter = CommentAdapter(commentList)
        recyclerView.adapter = commentAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        submitButton.setOnClickListener {
            val commentText = commentEditText.text.toString().trim()
            if (commentText.isNotEmpty()) {
                val newComment = Comment(commentText)
                commentAdapter.addComment(newComment)
                commentEditText.text.clear()
            }
        }
    }
}
