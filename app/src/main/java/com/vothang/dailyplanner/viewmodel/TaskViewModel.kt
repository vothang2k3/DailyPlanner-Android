package com.vothang.dailyplanner.viewmodel

import androidx.lifecycle.ViewModel
import com.vothang.dailyplanner.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.listOf

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow(
        listOf (
            Task(id = 1, title = "Học Jetpack Compose", listId = 1),
            Task(id = 2, title = "Làm bài tập Room", isDone = true, listId = 1),
            Task(id = 3, title = "Review code", note = "PR #42", listId = 1)
        )
    )

    // asStateFlow đảm bảo bên ngoài không thể modifier trực tiếp
    val tasks : StateFlow<List<Task>> = _tasks.asStateFlow()


}