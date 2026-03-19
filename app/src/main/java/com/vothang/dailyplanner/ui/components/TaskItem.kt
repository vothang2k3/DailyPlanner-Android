package com.vothang.dailyplanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.vothang.dailyplanner.model.Task
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// Compose nhận state và 2 callback, không dữ state - chỉ hiển thị
@Composable
fun TaskItem(
    task: Task,
    onToggleDone: (Task) -> Unit,
    onClickItem: (Task) -> Unit
) {
    val now = System.currentTimeMillis()
    val isOverdue = task.deadline != null && task.deadline < now && !task.isDone

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(task) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { onToggleDone(task) }
        )

        Column (
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if(task.isDone) TextDecoration.LineThrough else TextDecoration.None
                ),
                color = if (task.isDone) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )


            if(task.deadline != null) {
                val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("vi-VN"))
                    .format(Date(task.deadline))
                Text(
                    text = formatted,
                    style = MaterialTheme.typography.bodySmall,
                    color = if(isOverdue) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

