package com.example.projectmate

sealed class TodoDisplayItem {
    data class DateHeader(val date: String) : TodoDisplayItem()
    data class TodoData(val todo: TodoItem) : TodoDisplayItem()
}