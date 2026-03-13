package com.vothang.dailyplanner.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text


@Composable
fun AddEditTaskScreen(
    taskId: Int? = null
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val label = if (taskId == null) "thêm task" else "sửa task $taskId"
        Text(text = label)
    }
}