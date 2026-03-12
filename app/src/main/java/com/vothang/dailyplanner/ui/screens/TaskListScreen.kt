package com.vothang.dailyplanner.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen() {
    // Dữ liệu hardcore vào 5 tasks
    val tasks = listOf(
        Task("1", "Học Jetpack Compose cơ bản", isDone = true),
        Task("2", "Tạo giao diện TaskItem", isDone = true),
        Task("3", "Tạo màn hình TaskListScreen", note = "Dùng LazyColumn"),
        Task("4", "Cấu hình Theme cho App"),
        Task("5", "Chạy thử trên Emulator", note = "Kiểm tra hiển thị")
    )

    Scaffold(
        topBar = {
            TopAppBar( title = { Text("Daily Planner") })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { /* Sẽ xử lý sau*/ }
                )
            }
        }
    }
}