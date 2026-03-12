package com.vothang.dailyplanner.model

data class Task(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
    val note: String = "",
    val deadline: Long? = null,
    val listId: Int = 0
)
