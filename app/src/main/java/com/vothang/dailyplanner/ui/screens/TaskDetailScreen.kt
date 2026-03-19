package com.vothang.dailyplanner.ui.screens

import android.R.attr.navigationIcon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.vothang.dailyplanner.ui.navigation.EditTask
import com.vothang.dailyplanner.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    backStack: NavBackStack<NavKey>,
    taskViewModel: TaskViewModel = hiltViewModel()
) {
    val tasks by taskViewModel.tasks.collectAsState()
    // Lấy task từ StateFlow thay vì gọi hàm riêng, tự động cập nhật nếu task thay đổi
    val task = tasks.find { it.id == taskId }

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(task) {
        // Nếu task không tìm thấy (vừa bị xoá), tự động back
        if(task == null) {
            backStack.removeLastOrNull()
        }
    }
    if(task == null) return

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xoá task") },
            text = { Text("Bạn có chắc muốn xoá \"${task.title}\" không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskViewModel.delete(task)
                        showDeleteDialog = false
                        backStack.removeLastOrNull()
                    }
                ) {
                    Text("Xoá", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Huỷ")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết task") },
                navigationIcon = {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = { backStack.add(EditTask(taskId)) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Sửa")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Xoá")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall
            )

            if (task.note.isNotBlank()) {
                Text(
                    text = task.note,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (task.deadline != null) {
                val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("vi-VN"))
                    .format(Date(task.deadline))
                Text(
                    text = "Deadline: $formatted",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row {
                Text(
                    text = "Trạng thái: ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = if (task.isDone) "Đã hoàn thành" else "Chưa xong",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (task.isDone)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}