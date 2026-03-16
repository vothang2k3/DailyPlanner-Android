package com.vothang.dailyplanner.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text


@Composable
fun AddEditTaskScreen(
    listId: Int? = null,
    taskId: Int? = null
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val label = when {
            taskId != null -> "Sửa task $taskId"
            listId != null -> "Sửa list $listId"
            else -> "Thêm task"
        }
        Text(text = label)
    }
}