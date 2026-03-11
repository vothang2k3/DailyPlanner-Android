package com.example.googletasksclone.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen() {
    val currentTime = System.currentTimeMillis()
    val oneDay = 86400000L // Số mili-giây trong 1 ngày (24 * 60 * 60 * 1000)

    val tasks = remember {
        mutableStateOf(
            listOf(
                // Task có hạn sau 3 ngày
                Task(
                    id = 1,
                    title = "Buy groceries",
                    note = "Milk, eggs, bread",
                    isDone = false,
                    deadline = currentTime + (3 * oneDay),
                    listId = 1
                ),

                // Task không có hạn, đã hoàn thành
                Task(id = 2, title = "Call dentist", note = null, isDone = true, deadline = null, listId = 1),

                // Task ĐÃ QUÁ HẠN 1 ngày (để test chữ màu đỏ ở Ngày 11)
                Task(id = 3, title = "Finish report", note = "Q1 performance analysis", isDone = false, deadline = currentTime - (1 * oneDay), listId = 1),

                // Task không có hạn
                Task(id = 4, title = "Gym workout", note = "Leg day", isDone = false, deadline = null, listId = 1),

                // Task có hạn sau 7 ngày
                Task(id = 5, title = "Read book", note = "Chapter 5-7", isDone = false, deadline = currentTime + (7 * oneDay), listId = 1)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = tasks.value,
                key = { task -> task.id }
            ) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { isChecked ->
                        // Update task completion status
                        tasks.value = tasks.value.map {
                            if (it.id == task.id) it.copy(isDone = isChecked)
                            else it
                        }
                    }
                )
            }
        }
    }
}