package com.vothang.dailyplanner.model

data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean,
    val note : String,
    val deadline: Long?,
    val listId: Int
)
