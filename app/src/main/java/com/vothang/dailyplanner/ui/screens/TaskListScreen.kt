package com.vothang.dailyplanner.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.ui.components.TaskItem
import com.vothang.dailyplanner.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel = hiltViewModel()
) {
    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Plan") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(
                items = tasks,
                key = { task -> task.id }
            ) { task ->
                TaskItem(
                    task = task,
                    onToggleDone = { /* Xử lý sau */ },
                    onClickItem = { /* Xử lý sau */ }
                )
            }
        }
    }
}