package com.vothang.dailyplanner.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.ui.components.TaskItem
import com.vothang.dailyplanner.viewmodel.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel // Import quan trọng để dùng viewModel()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar( title = { Text("Daily Planner") })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { isChecked ->
                        // Báo cáo sự kiện ngược lên ViewModel (Hoist state)
                        viewModel.onTaskCheckedChange(task.id, isChecked)
                    }
                )
            }
        }
    }
}