package com.vothang.dailyplanner.viewmodel

import androidx.lifecycle.ViewModel
import com.vothang.dailyplanner.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.listOf

class TaskViewModel : ViewModel() {
    // Nguồn dữ liệu nội bộ (có thể sửa)
    private val _tasks = MutableStateFlow(
        listOf(
            Task("1", "Học ViewModel Lifecycle", isDone = true),
            Task("2", "Tìm hiểu StateFlow", isDone = true),
            Task("3", "Tạo TaskViewModel", note = "Chưa dùng Hilt"),
            Task("4", "Cập nhật TaskListScreen"),
            Task("5", "Chạy thử ứng dụng")
        )
    )

    // Dữ liệu bộc lộ ra UI (chỉ đọc)
    val tasks: StateFlow<List<Task>> = _tasks

    // Hàm UI gọi khi người dùng bấm vào Checkbox (Tạm thời để khung)
    fun onTaskCheckedChange(taskId: String, isDone: Boolean) {
        // Xử lý logic update ở đây
    }
}