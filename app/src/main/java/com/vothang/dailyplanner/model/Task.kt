package com.vothang.dailyplanner.model

data class Task(
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false,
    val note: String = "",
    val deadline: Long? = null,
    val listId: Int
)
