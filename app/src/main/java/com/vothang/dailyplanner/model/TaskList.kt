package com.vothang.dailyplanner.model

// Interface định nghĩa constract - ViewModel chỉ phụ thuộc vào interface này, không biết Room.
// Suspend cho các hàm write, FLow cho read để observe realtime
data class TaskList (
    val id: Int = 0,
    val name: String = ""
)