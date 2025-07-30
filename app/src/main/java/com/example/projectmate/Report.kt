package com.example.projectmate

data class Report(
    val comments: List<Comment> = listOf(),
    val todoList: List<String> = listOf(),
    val date: String = "",
    val comment: String = ""
)


