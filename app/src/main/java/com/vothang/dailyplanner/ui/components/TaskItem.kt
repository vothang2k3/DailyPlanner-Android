package com.vothang.dailyplanner.ui.components

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
import androidx.compose.ui.tooling.preview.Preview
import com.vothang.dailyplanner.model.Task
import androidx.compose.ui.unit.dp


@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
            if(!task.note.isNullOrEmpty()) {
                Text(text = task.note, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task(id = "1", title = "Học Jetpack Compose", note = "Hoàn thành bài tập ngày 3"),
        onCheckedChange = {}
    )
}

